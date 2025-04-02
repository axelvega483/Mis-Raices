package com.MisRaices.demo;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.service.CategoriaService;
import com.MisRaices.demo.service.ProductoService;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner initDataBase(CategoriaService categoriaService, ProductoService productoService) {
        return arg -> {
            if (categoriaService.listar().isEmpty()) {
                Categoria arbol = new Categoria();
                arbol.setNombre("Árbol");
                arbol.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743356344/vqrgka5kb4xjr3mxdbeh.jpg");

                Categoria aromatica = new Categoria();
                aromatica.setNombre("Aromaticas");
                aromatica.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743356344/cvvf1j4usladzfcg0nrn.jpg");

                Categoria cactus = new Categoria();
                cactus.setNombre("Cactus");
                cactus.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743356344/haobplq9xncxrhmmw9oq.jpg");

                Categoria exterior = new Categoria();
                exterior.setNombre("Exterior");
                exterior.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743356344/wqbz6qaksegw6c9vjrdd.jpg");

                Categoria interior = new Categoria();
                interior.setNombre("Interior");
                interior.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743356344/t3vu1pp2g5pxxxpfiefy.jpg");

                Categoria suculenta = new Categoria();
                suculenta.setNombre("Suculenta");
                suculenta.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743356344/nqcoux5ujrrqkhjgv6bo.jpg");
               

                categoriaService.guardar(arbol);
                categoriaService.guardar(aromatica);
                categoriaService.guardar(cactus);
                categoriaService.guardar(exterior);
                categoriaService.guardar(interior);
                categoriaService.guardar(suculenta);
                System.out.println("Categorias cargadas");
            }
            if (productoService.listar().isEmpty()) {
                Optional<Categoria> arbol = categoriaService.obtenerNombre("Árbol");
                Optional<Categoria> aromaticas = categoriaService.obtenerNombre("Aromaticas");
                Optional<Categoria> cactus = categoriaService.obtenerNombre("Cactus");
                Optional<Categoria> exterior = categoriaService.obtenerNombre("Exterior");
                Optional<Categoria> interior = categoriaService.obtenerNombre("Interior");
                Optional<Categoria> suculenta = categoriaService.obtenerNombre("Suculenta");

                Producto bracho = new Producto();
                bracho.setCategoria(arbol.get());
                bracho.setNombre("Bracho");
                bracho.setDescripcion("40cm, bolsa 1L");
                bracho.setPrecio(1000.0);
                bracho.setStock(8);
                bracho.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743618906/bracho-40cm-bolsa_1l-_1000_tsglxb.jpg");

                Producto chivato = new Producto();
                chivato.setCategoria(arbol.get());
                chivato.setNombre("Chivato");
                chivato.setDescripcion("50cm bolsa 5L");
                chivato.setPrecio(3000.0);
                chivato.setStock(7);
                chivato.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743618964/chivato-bolsa_5l-_3000_lyfoep.jpg");

                Producto guayacan = new Producto();
                guayacan.setCategoria(arbol.get());
                guayacan.setNombre("Guayacan");
                guayacan.setDescripcion("80cm bolsa 5L");
                guayacan.setPrecio(1800.0);
                guayacan.setStock(5);
                guayacan.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619016/Guayac%C3%A1n-bolsa_5l-_80cm-_1800_os7byl.jpg");

                Producto thevetia = new Producto();
                thevetia.setCategoria(arbol.get());
                thevetia.setNombre("Thevetia");
                thevetia.setDescripcion("Recipiente descartable 1m");
                thevetia.setPrecio(2800.0);
                thevetia.setStock(9);
                thevetia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619047/thevetia-_recipiente_descartable-_1m-_2800_oxevqi.jpg");

                Producto boldo = new Producto();
                boldo.setCategoria(aromaticas.get());
                boldo.setNombre("Boldo Paraguayo");
                boldo.setDescripcion("Maceta N°12");
                boldo.setPrecio(1200.0);
                boldo.setStock(5);
                boldo.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619143/boldo_paraguayo-_maceta_n12-_1200_w1ady2.jpg");

                Producto incienso = new Producto();
                incienso.setCategoria(aromaticas.get());
                incienso.setNombre("Incienso");
                incienso.setDescripcion("Maceta N°12");
                incienso.setPrecio(1500.0);
                incienso.setStock(10);
                incienso.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619185/incienso-_maceta_n12-_1500_d2srrf.jpg");

                Producto romero = new Producto();
                romero.setCategoria(aromaticas.get());
                romero.setNombre("Romero");
                romero.setDescripcion("Maceta soplada N°12");
                romero.setPrecio(800.0);
                romero.setStock(9);
                romero.setImg("https://drive.google.com/uc?export=view&id=1kNiOhx5I0quXcf2g1zVvFKjlGzJS-MtV");

                Producto mammillaria = new Producto();
                mammillaria.setCategoria(cactus.get());
                mammillaria.setNombre("Mammillaria Elongata");
                mammillaria.setDescripcion("Maceta N°6");
                mammillaria.setPrecio(500.0);
                mammillaria.setStock(15);
                mammillaria.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619340/mammillaria_elongata-_maceta_n6-_500_w3nj5f.jpg");

                Producto gracilis = new Producto();
                gracilis.setCategoria(cactus.get());
                gracilis.setNombre("Mammillaria Gracilis");
                gracilis.setDescripcion("Maceta N°8");
                gracilis.setPrecio(700.0);
                gracilis.setStock(13);
                gracilis.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619393/mammillaria_gracilis-_maceta_n8-_700_taopo0.jpg");

                Producto plumosa = new Producto();
                plumosa.setCategoria(cactus.get());
                plumosa.setNombre("Mammillaria Plumosa");
                plumosa.setDescripcion("Maceta N°6");
                plumosa.setPrecio(500.0);
                plumosa.setStock(8);
                plumosa.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619439/mammillaria_plumosa-_maceta_n6-_500_lbgxtf.jpg");

                Producto prolifera = new Producto();
                prolifera.setCategoria(cactus.get());
                prolifera.setNombre("Mammillaria Prolifera");
                prolifera.setDescripcion("Maceta N°8");
                prolifera.setPrecio(700.0);
                prolifera.setStock(7);
                prolifera.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619494/mammillaria_prolifera-_maceta_n8-_700_cfke52.jpg");

                Producto opuntia = new Producto();
                opuntia.setCategoria(cactus.get());
                opuntia.setNombre("Opuntia Humifusa");
                opuntia.setDescripcion("Maceta soplada N°12");
                opuntia.setPrecio(700.0);
                opuntia.setStock(8);
                opuntia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619530/opuntia_humifusa-_maceta_soplada_n12-_700_ylpa71.jpg");

                Producto albuca = new Producto();
                albuca.setCategoria(exterior.get());
                albuca.setNombre("Albuca Bracteata");
                albuca.setDescripcion("Maceta N°18");
                albuca.setPrecio(3500.0);
                albuca.setStock(5);
                albuca.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619756/albuca_bracteata-_maceta_n18-_3500_d0udxv.jpg");

                Producto euphorbia = new Producto();
                euphorbia.setCategoria(exterior.get());
                euphorbia.setNombre("Euphorbia Umbellata");
                euphorbia.setDescripcion("Maceta soplada N°16");
                euphorbia.setPrecio(2100.0);
                euphorbia.setStock(4);
                euphorbia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619853/euphorbia_umbellata-_maceta_soplada_n16-_2100_gzwrte.jpg");

                Producto rayito = new Producto();
                rayito.setCategoria(exterior.get());
                rayito.setNombre("Rayito de Sol");
                rayito.setDescripcion("Maceta Bols N°8");
                rayito.setPrecio(2800.0);
                rayito.setStock(4);
                rayito.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619961/Rayito_de_sol-_maceta_bolsa_n18_colgante-_2800_j2nuky.jpg");

                Producto tradescantia = new Producto();
                tradescantia.setCategoria(exterior.get());
                tradescantia.setNombre("Tradescantia Pallida");
                tradescantia.setDescripcion("Bolsa 5L");
                tradescantia.setPrecio(2300.0);
                tradescantia.setStock(6);
                tradescantia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620079/tradescantia_pallida-bolsa_5l-_2300_yzytcp.jpg");

                Producto helecho = new Producto();
                helecho.setCategoria(interior.get());
                helecho.setNombre("Helecho Serrucho");
                helecho.setDescripcion("Maceta N°15");
                helecho.setPrecio(400.0);
                helecho.setStock(4);
                helecho.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620139/helecho_serrucho-_maceta_n15-_4000_eol1jv.jpg");

                Producto brasil = new Producto();
                brasil.setCategoria(interior.get());
                brasil.setNombre("Philodendron Brasil");
                brasil.setDescripcion("Maceta soplada N°12");
                brasil.setPrecio(2000.0);
                brasil.setStock(6);
                brasil.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620217/philodendron_brasil-_maceta_soplada_n12-_2000_jcmt0w.jpg");
        
                Producto plateado = new Producto();
                plateado.setCategoria(interior.get());
                plateado.setNombre("Philodendron Plateado");
                plateado.setDescripcion("Maceta N°14");
                plateado.setPrecio(3000.0);
                plateado.setStock(3);
                plateado.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620289/philodendron_plateado-maceta_n14-_3000_whoxet.jpg");

                Producto sanseviera = new Producto();
                sanseviera.setCategoria(interior.get());
                sanseviera.setNombre("Sansevieria Enana");
                sanseviera.setDescripcion("Maceta N°15");
                sanseviera.setPrecio(2800.0);
                sanseviera.setStock(7);
                sanseviera.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620336/Sansevieria_enana-_maceta_n15-_2800_m7qdqi.jpg");

                Producto crassula = new Producto();
                crassula.setCategoria(suculenta.get());
                crassula.setNombre("Creassula");
                crassula.setDescripcion("Maceta soplada N°12");
                crassula.setPrecio(1600.0);
                crassula.setStock(9);
                crassula.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620423/crassula_-_maceta_soplada_n12_-_1600_njcgd7.jpg");

                Producto echeveria = new Producto();
                echeveria.setCategoria(suculenta.get());
                echeveria.setNombre("Echeveria Elegans");
                echeveria.setDescripcion("Maceta soplada N°12");
                echeveria.setPrecio(3000.0);
                echeveria.setStock(7);
                echeveria.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620491/echeveria_elegans_-_maceta_soplada_n12-_3000_g2xinr.jpg");

                Producto haworthia = new Producto();
                haworthia.setCategoria(suculenta.get());
                haworthia.setNombre("Haworthia Cymbiformis");
                haworthia.setDescripcion("Maceta N°12");
                haworthia.setPrecio(2300.0);
                haworthia.setStock(5);
                haworthia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620541/haworthia_cymbiformis-_maceta_n12-_2300_zmk42j.jpg");

                Producto kalanchoe = new Producto();
                kalanchoe.setCategoria(suculenta.get());
                kalanchoe.setNombre("Kalanchoe Humilis");
                kalanchoe.setDescripcion("Maceta soplada N°12");
                kalanchoe.setPrecio(1500.0);
                kalanchoe.setStock(9);
                kalanchoe.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620589/kalanchoe_humilis._maceta_soplada_n12-_1500_cnaz9d.jpg");

                productoService.guardar(bracho);
                productoService.guardar(chivato);
                productoService.guardar(guayacan);
                productoService.guardar(thevetia);
                productoService.guardar(boldo);
                productoService.guardar(incienso);
                productoService.guardar(romero);
                productoService.guardar(mammillaria);
                productoService.guardar(gracilis);
                productoService.guardar(plumosa);
                productoService.guardar(prolifera);
                productoService.guardar(opuntia);
                productoService.guardar(albuca);
                productoService.guardar(euphorbia);
                productoService.guardar(rayito);
                productoService.guardar(tradescantia);
                productoService.guardar(helecho);
                productoService.guardar(brasil);
                productoService.guardar(plateado);
                productoService.guardar(sanseviera);
                productoService.guardar(crassula);
                productoService.guardar(echeveria);
                productoService.guardar(haworthia);
                productoService.guardar(kalanchoe);

                System.out.println("Productos cargados");
            }
        };
    }

}
