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
                arbol.setImg("https://drive.google.com/uc?export=view&id=1oTZIu4m1jOQrIrUVozqhkyAwikKp7oBH");

                Categoria aromatica = new Categoria();
                aromatica.setNombre("Aromaticas");
                aromatica.setImg("https://drive.google.com/uc?export=view&id=1KfTNa_axTFHjWvMvHtkV43F7RQnG-oP9");

                Categoria cactus = new Categoria();
                cactus.setNombre("Cactus");
                cactus.setImg("https://drive.google.com/uc?export=view&id=1J7eZM04Q-RR8SDUXkppXyHDw7BwgCJST");

                Categoria exterior = new Categoria();
                exterior.setNombre("Exterior");
                exterior.setImg("https://drive.google.com/uc?export=view&id=1zIP63l_xwy0U71YpMU-nmqDViXJ23Hmu");

                Categoria interior = new Categoria();
                interior.setNombre("Interior");
                interior.setImg("https://drive.google.com/uc?export=view&id=1eHXg0K3hGcc2ZCgIvWFeRsROGFsJRQJp");

                Categoria suculenta = new Categoria();
                suculenta.setNombre("Suculenta");
                suculenta.setImg("https://drive.google.com/uc?export=view&id=1os_VWMI-XhJ_dkgKxB3UhxV5NLoeGHnI");
               

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
                bracho.setImg("https://drive.google.com/uc?export=view&id=19WLuhGby3ECODil4U1SVMVLg2Mt25O2U");

                Producto chivato = new Producto();
                chivato.setCategoria(arbol.get());
                chivato.setNombre("Chivato");
                chivato.setDescripcion("50cm bolsa 5L");
                chivato.setPrecio(3000.0);
                chivato.setStock(7);
                chivato.setImg("https://drive.google.com/uc?export=view&id=1j1pNMKseYDJW_1OYNJYIPGfVcfCKzb3-");

                Producto guayacan = new Producto();
                guayacan.setCategoria(arbol.get());
                guayacan.setNombre("Guayacan");
                guayacan.setDescripcion("80cm bolsa 5L");
                guayacan.setPrecio(1800.0);
                guayacan.setStock(5);
                guayacan.setImg("https://drive.google.com/uc?export=view&id=1Z-v15tzfXwIrnJZlqyydKIB9Pn4SaVmX");

                Producto thevetia = new Producto();
                thevetia.setCategoria(arbol.get());
                thevetia.setNombre("Thevetia");
                thevetia.setDescripcion("Recipiente descartable 1m");
                thevetia.setPrecio(2800.0);
                thevetia.setStock(9);
                thevetia.setImg("https://drive.google.com/uc?export=view&id=1Qic2SVw5LOPmglfcmK_sxWm3MQqmibOy");

                Producto boldo = new Producto();
                boldo.setCategoria(aromaticas.get());
                boldo.setNombre("Boldo Paraguayo");
                boldo.setDescripcion("Maceta N°12");
                boldo.setPrecio(1200.0);
                boldo.setStock(5);
                boldo.setImg("https://drive.google.com/uc?export=view&id=1GcVwIjJ7dlnHSprmEOm28RYJN0Tqm0CK");

                Producto incienso = new Producto();
                incienso.setCategoria(aromaticas.get());
                incienso.setNombre("Incienso");
                incienso.setDescripcion("Maceta N°12");
                incienso.setPrecio(1500.0);
                incienso.setStock(10);
                incienso.setImg("https://drive.google.com/uc?export=view&id=1ROO2l4dxEwhUqSAOHY9b0kT86fUTbDX1");

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
                mammillaria.setImg("https://drive.google.com/uc?export=view&id=1VFXXfFW_wZzFYSQnGCZui78RHYecsUrU");

                Producto gracilis = new Producto();
                gracilis.setCategoria(cactus.get());
                gracilis.setNombre("Mammillaria Gracilis");
                gracilis.setDescripcion("Maceta N°8");
                gracilis.setPrecio(700.0);
                gracilis.setStock(13);
                gracilis.setImg("https://drive.google.com/uc?export=view&id=1skTlxpDKG5NieskR4Jk2WFNP9vK0zEFT");

                Producto plumosa = new Producto();
                plumosa.setCategoria(cactus.get());
                plumosa.setNombre("Mammillaria Plumosa");
                plumosa.setDescripcion("Maceta N°6");
                plumosa.setPrecio(500.0);
                plumosa.setStock(8);
                plumosa.setImg("https://drive.google.com/uc?export=view&id=1u-N2fQTreyGBe1MUSURkijsiM2cqLv_g");

                Producto prolifera = new Producto();
                prolifera.setCategoria(cactus.get());
                prolifera.setNombre("Mammillaria Prolifera");
                prolifera.setDescripcion("Maceta N°8");
                prolifera.setPrecio(700.0);
                prolifera.setStock(7);
                prolifera.setImg("https://drive.google.com/uc?export=view&id=1tVErbO-o3bLUHngSf0TPRVRC3agOhBMY");

                Producto opuntia = new Producto();
                opuntia.setCategoria(cactus.get());
                opuntia.setNombre("Opuntia Humifusa");
                opuntia.setDescripcion("Maceta soplada N°12");
                opuntia.setPrecio(700.0);
                opuntia.setStock(8);
                opuntia.setImg("https://drive.google.com/uc?export=view&id=1AfXo3RhrHJ07sVTUqrOL7gCSXaellj3o");

                Producto asparagus = new Producto();
                asparagus.setCategoria(exterior.get());
                asparagus.setNombre("Asparagus");
                asparagus.setDescripcion("Maceta bols N°18 + colgate");
                asparagus.setPrecio(3600.0);
                asparagus.setStock(5);
                asparagus.setImg("https://drive.google.com/uc?export=view&id=1fQOQuXMscXLJcnvxSP48ddcvo-pyvHmw");

                Producto euphorbia = new Producto();
                euphorbia.setCategoria(exterior.get());
                euphorbia.setNombre("Euphorbia Umbellata");
                euphorbia.setDescripcion("Maceta soplada N°16");
                euphorbia.setPrecio(2100.0);
                euphorbia.setStock(4);
                euphorbia.setImg("https://drive.google.com/uc?export=view&id=1Ftc4vXRSsbP3-t2XNYjZVv8UqEWrxWsV");

                Producto hippeastrum = new Producto();
                hippeastrum.setCategoria(exterior.get());
                hippeastrum.setNombre("Hippeastrum Puniceum");
                hippeastrum.setDescripcion("Maceta N°15");
                hippeastrum.setPrecio(3500.0);
                hippeastrum.setStock(4);
                hippeastrum.setImg("https://drive.google.com/uc?export=view&id=17qFmSpFl_V6Jp9Dbn23JQMDoH8IzJsVx");

                Producto tradescantia = new Producto();
                tradescantia.setCategoria(exterior.get());
                tradescantia.setNombre("Tradescantia Pallida");
                tradescantia.setDescripcion("Bolsa 5L");
                tradescantia.setPrecio(2300.0);
                tradescantia.setStock(6);
                tradescantia.setImg("https://drive.google.com/uc?export=view&id=10M3MQMQ-XQoT1oMtfx9FyzrfdMqNURv-");

                Producto adam = new Producto();
                adam.setCategoria(interior.get());
                adam.setNombre("Costilla de Adam");
                adam.setDescripcion("Maceta N°21");
                adam.setPrecio(7000.0);
                adam.setStock(4);
                adam.setImg("https://drive.google.com/uc?export=view&id=1-s21py9QUL4yD_JGZw-0hDObBoHdvExU");

                Producto brasil = new Producto();
                brasil.setCategoria(interior.get());
                brasil.setNombre("Philodendron Brasil");
                brasil.setDescripcion("Maceta soplada N°12");
                brasil.setPrecio(2000.0);
                brasil.setStock(6);
                brasil.setImg("https://drive.google.com/uc?export=view&id=1eZy67ydu4Y8i_yN6j6ZrDuEnSCT7-8iL");
        
                Producto plateado = new Producto();
                plateado.setCategoria(interior.get());
                plateado.setNombre("Philodendron Plateado");
                plateado.setDescripcion("Maceta N°14");
                plateado.setPrecio(3000.0);
                plateado.setStock(3);
                plateado.setImg("https://drive.google.com/uc?export=view&id=193hW9OeNots7cUkyB3sZ0t_XUzkziYW2");

                Producto sanseviera = new Producto();
                sanseviera.setCategoria(interior.get());
                sanseviera.setNombre("Sansevieria Enana");
                sanseviera.setDescripcion("Maceta N°15");
                sanseviera.setPrecio(2800.0);
                sanseviera.setStock(7);
                sanseviera.setImg("https://drive.google.com/uc?export=view&id=1NaITsMA_33Og269Yh0GIVkm-KCHPGPpm");

                Producto aloe = new Producto();
                aloe.setCategoria(suculenta.get());
                aloe.setNombre("Aloe Aristata");
                aloe.setDescripcion("Maceta soplada N°12");
                aloe.setPrecio(2000.0);
                aloe.setStock(9);
                aloe.setImg("https://drive.google.com/uc?export=view&id=1phP8H_wwQ3hm2aSPPTgIz8kgCPkzUrlW");

                Producto echeveria = new Producto();
                echeveria.setCategoria(suculenta.get());
                echeveria.setNombre("Echeveria Elegans");
                echeveria.setDescripcion("Maceta soplada N°12");
                echeveria.setPrecio(3000.0);
                echeveria.setStock(7);
                echeveria.setImg("https://drive.google.com/uc?export=view&id=159unr1rwTV-WSpiGVwjYjEqAjknTUIWd");

                Producto haworthia = new Producto();
                haworthia.setCategoria(suculenta.get());
                haworthia.setNombre("Haworthia Cymbiformis");
                haworthia.setDescripcion("Maceta N°12");
                haworthia.setPrecio(2300.0);
                haworthia.setStock(5);
                haworthia.setImg("https://drive.google.com/uc?export=view&id=163Gyfp8MYNr53YF5LbZ9otBKKFY1iPtp");

                Producto kalanchoe = new Producto();
                kalanchoe.setCategoria(suculenta.get());
                kalanchoe.setNombre("Kalanchoe Humilis");
                kalanchoe.setDescripcion("Maceta soplada N°12");
                kalanchoe.setPrecio(1500.0);
                kalanchoe.setStock(9);
                kalanchoe.setImg("https://drive.google.com/uc?export=view&id=1VbE2S-nHsVehJTgUn-TZAMh9DirdfD8G");

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
                productoService.guardar(asparagus);
                productoService.guardar(euphorbia);
                productoService.guardar(hippeastrum);
                productoService.guardar(tradescantia);
                productoService.guardar(adam);
                productoService.guardar(brasil);
                productoService.guardar(plateado);
                productoService.guardar(sanseviera);
                productoService.guardar(aloe);
                productoService.guardar(echeveria);
                productoService.guardar(haworthia);
                productoService.guardar(kalanchoe);

                System.out.println("Productos cargados");
            }
        };
    }

}
