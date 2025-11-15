package com.MisRaices.ProyectoFinal.service;

import com.MisRaices.ProyectoFinal.DTOS.PedidoDTO.PedidoDetallePostDTO;
import com.MisRaices.ProyectoFinal.DTOS.PedidoDTO.PedidoGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.PedidoDTO.PedidoMapper;
import com.MisRaices.ProyectoFinal.DTOS.PedidoDTO.PedidoPostDTO;
import com.MisRaices.ProyectoFinal.entity.*;
import com.MisRaices.ProyectoFinal.interfaz.PedidoInterfaz;
import com.MisRaices.ProyectoFinal.repository.PedidoRepository;
import com.MisRaices.ProyectoFinal.repository.ProductoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.MisRaices.ProyectoFinal.repository.TarjetaCreditoRepository;
import com.MisRaices.ProyectoFinal.repository.UsuarioRepository;
import com.MisRaices.ProyectoFinal.util.EstadoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService implements PedidoInterfaz {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private PedidoMapper mapper;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private TarjetaCreditoRepository tarjetaRepo;


    @Override
    public PedidoGetDTO crear(PedidoPostDTO postDTO) {
        Usuario cliente = usuarioRepo.findById(postDTO.getUsuario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no válido"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(cliente);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);

        List<PedidoDetalle> detalles = procesarDetallesPedido(postDTO.getDetalle(), pedido);
        pedido.setDetalle(detalles);
        double total = calcularTotal(detalles);
        pedido.setTotal(total);
        Pedido pedidoGuardado = repo.save(pedido);
        return mapper.toDTO(pedidoGuardado);
    }

    private List<PedidoDetalle> procesarDetallesPedido(List<PedidoDetallePostDTO> detalleDTOs, Pedido pedido) {
        List<PedidoDetalle> detalles = new ArrayList<>();

        for (PedidoDetallePostDTO detDto : detalleDTOs) {
            Producto producto = productoRepo.findById(detDto.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID " + detDto.getProducto().getId()));

            if (producto.getStock() < detDto.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para producto " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - detDto.getCantidad());
            productoRepo.save(producto);

            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(detDto.getCantidad());
            detalle.setPedido(pedido);

            detalles.add(detalle);
        }

        return detalles;
    }

    private double calcularTotal(List<PedidoDetalle> detalles) {
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getProducto().getPrecio() * detalle.getCantidad())
                .sum();
    }

    @Override
    public Optional<PedidoGetDTO> obtener(Integer id) {
        return repo.findById(id).map(pedido -> mapper.toDTO(pedido));
    }

    @Override
    public List<PedidoGetDTO> listar() {
        return mapper.dtoList(repo.findAll());
    }

    @Override
    @Transactional
    public PedidoGetDTO finalizarCompra(Integer pedidoId, Integer tarjetaId) {
        Pedido pedido = repo.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID " + pedidoId));

        procesarStockProductos(pedido);

        procesarPagoTarjeta(pedido, tarjetaId);

        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.FACTURADO);
        Pedido pedidoFinalizado = repo.save(pedido);


        return mapper.toDTO(pedidoFinalizado);
    }

    private void procesarStockProductos(Pedido pedido) {
        for (PedidoDetalle detalle : pedido.getDetalle()) {
            Producto producto = productoRepo.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID " + detalle.getProducto().getId()));

            int nuevoStock = producto.getStock() - detalle.getCantidad();
            if (nuevoStock < 0) {
                throw new IllegalArgumentException("Stock insuficiente para producto: " + producto.getNombre());
            }

            producto.setStock(nuevoStock);
            productoRepo.save(producto);
        }
    }

    private void procesarPagoTarjeta(Pedido pedido, Integer tarjetaId) {
        TarjetaCredito tarjeta = tarjetaRepo.findById(tarjetaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarjeta no encontrada con ID " + tarjetaId));

        if (tarjeta.getSaldo() < pedido.getTotal()) {
            throw new IllegalArgumentException("Saldo insuficiente en la tarjeta");
        }

        tarjeta.setSaldo(tarjeta.getSaldo() - pedido.getTotal());
        tarjetaRepo.save(tarjeta);
    }

    @Override
    public Optional<Pedido> obtenerEntity(Integer id) {
        return repo.findById(id);
    }

}
