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
                arbol.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743705130/yohoqios9yl91zqlqoyp.jpg");

                Categoria aromatica = new Categoria();
                aromatica.setNombre("Aromaticas");
                aromatica.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743705125/r1pa7ljr4t7r4rf4gemc.jpg");

                Categoria cactus = new Categoria();
                cactus.setNombre("Cactus");
                cactus.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743705383/iqbibvg2sylpgddp2bxu.jpg");

                Categoria exterior = new Categoria();
                exterior.setNombre("Exterior");
                exterior.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743705124/tkppzqmoxhoziiwikvr7.jpg");

                Categoria interior = new Categoria();
                interior.setNombre("Interior");
                interior.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743705125/qalc8jafkaq0ld84jxat.jpg");

                Categoria suculenta = new Categoria();
                suculenta.setNombre("Suculenta");
                suculenta.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743705123/c1jqssmi4xcrbtm10csk.jpg");

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
                bracho.setCuidado("Árbol perenne de 8 a 10 m de altura con copa piramidal, ideal para plantar en jardines y paseos. Requiere exposición a pleno sol y suelos bien drenados. Es resistente a la sequía una vez establecido, pero se beneficia de riegos regulares durante períodos secos. Puede soportar heladas débiles siendo un árbol jóven.");
                bracho.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743618906/bracho-40cm-bolsa_1l-_1000_tsglxb.jpg");
                bracho.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749244040/kiammsez6zee363cuh2l.mp4");

                Producto chivato = new Producto();
                chivato.setCategoria(arbol.get());
                chivato.setNombre("Chivato");
                chivato.setDescripcion("50cm bolsa 5L");
                chivato.setPrecio(3000.0);
                chivato.setStock(7);
                chivato.setCuidado("Árbol caducifolio de 6-8 m de altura, con la copa aparasolada. Prefiere climas cálidos y soleados. Necesita suelos bien drenados y riegos moderados. Planta muy apreciada en jardinería por su espectacular floración de color rojo intenso. Se debe plantar en sitios amplios, sin construcciones ya que posee un sistema radicular agresivo. Sensible al frío, no soporta heladas intensas y prolongadas.");
                chivato.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743618964/chivato-bolsa_5l-_3000_lyfoep.jpg");
                chivato.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749244824/a2sgisjjczoi3tj036dv.mp4");

                Producto guayacan = new Producto();
                guayacan.setCategoria(arbol.get());
                guayacan.setNombre("Guayacan");
                guayacan.setDescripcion("80cm bolsa 5L");
                guayacan.setPrecio(1800.0);
                guayacan.setStock(5);
                guayacan.setCuidado("Árbol caducifolio de copa aparasolada y porte grande, alcanza una altura de 20 m en condiciones favorables. Se destaca por su corteza delgada constituida por escamas pardo-verdosas que se desprenden dejando parches de color verde-grisáceos. Prospera en pleno sol y suelos bien drenados. Tolera la sequía gracias a su sistema radicular profundo, pero se recomienda riego regular en su etapa de crecimiento. Ideal para jardines amplios o parquizados.");
                guayacan.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619016/Guayac%C3%A1n-bolsa_5l-_80cm-_1800_os7byl.jpg");
                guayacan.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749244936/zstixerge0c0zsd1acje.mp4");

                Producto thevetia = new Producto();
                thevetia.setCategoria(arbol.get());
                thevetia.setNombre("Thevetia");
                thevetia.setDescripcion("Recipiente descartable 1m");
                thevetia.setPrecio(2800.0);
                thevetia.setStock(9);
                thevetia.setCuidado("Arbolito de 3 a 4 m de altura con copa frondosa, ideal para veredas o jardines pequeños. Necesita exposición a pleno sol y suelos bien drenados. Es resistente a la sequía y florece durante gran parte del año con flores amarillas llamativas. No soporta heladas intensas y prolongadas. Sus hojas, tallos, flores,  frutos y semillas poseen un látex tóxico para personas y mascotas, se recomienda lavado con agua y jabón en caso de contacto con la piel.");
                thevetia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619047/thevetia-_recipiente_descartable-_1m-_2800_oxevqi.jpg");
                thevetia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245049/ubuylxsvswxp98hs2yhu.mp4");

                Producto boldo = new Producto();
                boldo.setCategoria(aromaticas.get());
                boldo.setNombre("Boldo Paraguayo");
                boldo.setDescripcion("Maceta N°12");
                boldo.setPrecio(1200.0);
                boldo.setStock(5);
                boldo.setCuidado("Planta rastrera de follaje perenne y tupido, muy aromática. Prefiere sol suave o media sombra y suelos sueltos que drenen bien. No requiere riegos constantes; mantener el sustrato apenas húmedo es suficiente. Puede cultivarse en maceta o jardín, y la poda ayuda a mantener su forma. No se recomienda su consumo ya que posee compuestos tóxicos.");
                boldo.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619143/boldo_paraguayo-_maceta_n12-_1200_w1ady2.jpg");
                boldo.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245155/ezcshaceba54pzjpazm0.mp4");

                Producto incienso = new Producto();
                incienso.setCategoria(aromaticas.get());
                incienso.setNombre("Incienso");
                incienso.setDescripcion("Maceta N°12");
                incienso.setPrecio(1500.0);
                incienso.setStock(10);
                incienso.setCuidado("Planta rastrera con follaje variegado, muy aromática. Requiere luz indirecta y suelos bien drenados. No soporta temperaturas demasiado altas o bajas, ideal para climas templados. Es sensible al exceso de riego, por lo que se debe permitir que el sustrato se seque entre riegos.");
                incienso.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619185/incienso-_maceta_n12-_1500_d2srrf.jpg");
                incienso.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245236/t70vnwlqrztzmgaptl5t.mp4");

                Producto mammillaria = new Producto();
                mammillaria.setCategoria(cactus.get());
                mammillaria.setNombre("Mammillaria Elongata");
                mammillaria.setDescripcion("Maceta N°6");
                mammillaria.setPrecio(500.0);
                mammillaria.setStock(15);
                mammillaria.setCuidado("Cactus cilíndricos de porte rastrero de hasta 15 cm de longitud y flores blanco-amarillentas. Rápido crecimiento en condiciones favorables. Necesita mucha luz solar directa y riegos muy espaciados, especialmente en invierno. Se recomienda utilizar sustrato bien drenado. Ideal para macetas pequeñas.");
                mammillaria.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619340/mammillaria_elongata-_maceta_n6-_500_w3nj5f.jpg");
                mammillaria.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245332/hbbpftcyxo0vukxvtou3.mp4");

                Producto gracilis = new Producto();
                gracilis.setCategoria(cactus.get());
                gracilis.setNombre("Mammillaria Gracilis");
                gracilis.setDescripcion("Maceta N°8");
                gracilis.setPrecio(700.0);
                gracilis.setStock(13);
                gracilis.setCuidado("Son cactus de porte cespitoso que no suelen superar 10 cm de altura.  Las flores son pequeñas y de color amarillo o crema. Prefiere exposición solar directa y altas temperaturas. Necesita riegos moderados y suelos con buen drenaje. Dejar que el sustrato se seque completamente entre riegos para evitar pudrición de raíces.");
                gracilis.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619393/mammillaria_gracilis-_maceta_n8-_700_taopo0.jpg");
                gracilis.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245399/gr7xe9hprzfr2iqbazyl.mp4");

                Producto plumosa = new Producto();
                plumosa.setCategoria(cactus.get());
                plumosa.setNombre("Mammillaria Plumosa");
                plumosa.setDescripcion("Maceta N°6");
                plumosa.setPrecio(500.0);
                plumosa.setStock(8);
                plumosa.setCuidado("Cactus globoso de aspecto llamativo y floración delicada. Requiere sol pleno. Prospera en suelos bien drenados con riegos escasos ya que es tolerante a la sequía y muy sensible al exceso de agua.");
                plumosa.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619439/mammillaria_plumosa-_maceta_n6-_500_lbgxtf.jpg");
                plumosa.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245486/oe78ndawrtlneodkhelp.mp4");

                Producto prolifera = new Producto();
                prolifera.setCategoria(cactus.get());
                prolifera.setNombre("Mammillaria Prolifera");
                prolifera.setDescripcion("Maceta N°8");
                prolifera.setPrecio(700.0);
                prolifera.setStock(7);
                prolifera.setCuidado("Cactus pequeño de 10 cm de alto con flores blanco-amarillentas. Necesita exposición solar directa y riegos moderados a escasos. Es importante evitar el encharcamiento para prevenir enfermedades radiculares.");
                prolifera.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619494/mammillaria_prolifera-_maceta_n8-_700_cfke52.jpg");
                prolifera.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245676/hshfoawazuiy0qzildub.mp4");

                Producto opuntia = new Producto();
                opuntia.setCategoria(cactus.get());
                opuntia.setNombre("Opuntia Humifusa");
                opuntia.setDescripcion("Maceta soplada N°12");
                opuntia.setPrecio(700.0);
                opuntia.setStock(8);
                opuntia.setCuidado("Cactus de tallo aplanado de 15 a 30cm de alto.. Prefiere pleno sol y suelos con buen drenaje. Riegos esporádicos, permitiendo que el sustrato se seque completamente entre aplicaciones. Soporta perfectamente altas temperaturas.");
                opuntia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619530/opuntia_humifusa-_maceta_soplada_n12-_700_ylpa71.jpg");
                opuntia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245788/atepfk80j3iphoflfilo.mp4");

                Producto albuca = new Producto();
                albuca.setCategoria(exterior.get());
                albuca.setNombre("Albuca Bracteata");
                albuca.setDescripcion("Maceta N°18");
                albuca.setPrecio(3500.0);
                albuca.setStock(5);
                albuca.setCuidado("Planta bulbosa, perenne. Es una planta tóxica para mascotas y humanos, no se recomienda su ingesta. Soporta sol directo per no altas temperaturas, ya que sus hojas pueden sufrir quemaduras. Necesita un suelo con buen drenaje y riegos moderados, evitando el exceso de humedad que puede provocar pudrición del bulbo.");
                albuca.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619756/albuca_bracteata-_maceta_n18-_3500_d0udxv.jpg");
                albuca.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245892/npwd5ksil3y8jzcpm4dm.mp4");

                Producto euphorbia = new Producto();
                euphorbia.setCategoria(exterior.get());
                euphorbia.setNombre("Euphorbia Umbellata");
                euphorbia.setDescripcion("Maceta soplada N°16");
                euphorbia.setPrecio(2100.0);
                euphorbia.setStock(4);
                euphorbia.setCuidado("Arbusto perenne de 5 a 8 m de altura con follaje muy llamativo. Prefiere luz indirecta brillante y suelos bien drenados. Riegos moderados, dejando que el sustrato se seque entre aplicaciones. Es una planta muy sensible al frío, prefiere climas templados.");
                euphorbia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619853/euphorbia_umbellata-_maceta_soplada_n16-_2100_gzwrte.jpg");
                euphorbia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245994/wk2cgdfwb2vwrnehu6yy.mp4");

                Producto rayito = new Producto();
                rayito.setCategoria(exterior.get());
                rayito.setNombre("Rayito de Sol");
                rayito.setDescripcion("Maceta Bols N°8");
                rayito.setPrecio(2800.0);
                rayito.setStock(4);
                rayito.setCuidado("Planta de porte rastrero con hojas suculentas y floración abundante y llamativa. Va muy bien a pleno sol y florece mejor si recibe al menos 6 horas diarias de luz. Necesita riegos moderados y un sustrato liviano. Sus flores se abren con la luz del día y se cierran al atardecer, creando un efecto visual encantador. Prefiere climas cálidos.");
                rayito.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619961/Rayito_de_sol-_maceta_bolsa_n18_colgante-_2800_j2nuky.jpg");
                rayito.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246094/zkn5nucfilorox72b0n5.mp4");

                Producto tradescantia = new Producto();
                tradescantia.setCategoria(exterior.get());
                tradescantia.setNombre("Tradescantia Pallida");
                tradescantia.setDescripcion("Bolsa 5L");
                tradescantia.setPrecio(2300.0);
                tradescantia.setStock(6);
                tradescantia.setCuidado("Planta de porte rastrero con follaje muy llamativo de color púrpura intenso y floración delicada durante todo el año. Evitar la luz solar directa. Prefiere suelos bien drenados y riegos moderados, permitiendo que el sustrato se seque entre aplicaciones. Prefiere climas cálidos.");
                tradescantia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620079/tradescantia_pallida-bolsa_5l-_2300_yzytcp.jpg");
                tradescantia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246173/ypuuney1kwqxtrgi02cg.mp4");

                Producto helecho = new Producto();
                helecho.setCategoria(interior.get());
                helecho.setNombre("Helecho Serrucho");
                helecho.setDescripcion("Maceta N°15");
                helecho.setPrecio(400.0);
                helecho.setStock(4);
                helecho.setCuidado("Es un helecho robusto, ideal para principiantes. Prefiere la luz solar indirecta y ambientes húmedos. Se debe mantener el sustrato húmedo, pero no encharcado. Es importante no exponer la planta a aire acondicionado directo, calefacción u otro artefacto que pueda resecar demasiado el ambiente.");
                helecho.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620139/helecho_serrucho-_maceta_n15-_4000_eol1jv.jpg");
                helecho.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246247/vk8xbcr1pw8un9llbkuv.mp4");

                Producto brasil = new Producto();
                brasil.setCategoria(interior.get());
                brasil.setNombre("Philodendron Brasil");
                brasil.setDescripcion("Maceta soplada N°12");
                brasil.setPrecio(2000.0);
                brasil.setStock(6);
                brasil.setCuidado("Planta trepadora de fácil cuidado. con follaje muy llamativo por su patrón de colores. Tóxica para mascotas. Crece bien con mucha luz indirecta y humedad ambiental media. Requiere riegos regulares, dejando secar la capa superior del sustrato entre cada uno. Es importante no exponer la planta a aire acondicionado directo, calefacción u otro artefacto que pueda resecar demasiado el ambiente.");
                brasil.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620217/philodendron_brasil-_maceta_soplada_n12-_2000_jcmt0w.jpg");
                brasil.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246310/xvmszxtcc8ozfiryily5.mp4");

                Producto plateado = new Producto();
                plateado.setCategoria(interior.get());
                plateado.setNombre("Philodendron Plateado");
                plateado.setDescripcion("Maceta N°14");
                plateado.setPrecio(3000.0);
                plateado.setStock(3);
                plateado.setCuidado("Planta de hábito trepador con hojas muy llamativas de color verde con destellos plateados. Tóxica para mascotas. Necesita luz solar indirecta y suelos bien drenados. Riegos moderados, evitando el encharcamiento. Es importante no exponer la planta a aire acondicionado directo, calefacción u otro artefacto que pueda resecar demasiado el ambiente.");
                plateado.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620289/philodendron_plateado-maceta_n14-_3000_whoxet.jpg");
                plateado.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246493/dcdcfkht7nncwl8daxg5.mp4");

                Producto sanseviera = new Producto();
                sanseviera.setCategoria(interior.get());
                sanseviera.setNombre("Sansevieria Enana");
                sanseviera.setDescripcion("Maceta N°15");
                sanseviera.setPrecio(2800.0);
                sanseviera.setStock(7);
                sanseviera.setCuidado("Planta suculenta de interior, ideal para principiantes por su resistencia. Muy utilizada por su capacidad para purificar el aire. Tolera una amplia gama de condiciones de luz, aunque la exposición directa al sol durante un tiempo prolongado puede quemar sus hojas. Requiere riegos esporádicos y suelo bien drenado.");
                sanseviera.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620336/Sansevieria_enana-_maceta_n15-_2800_m7qdqi.jpg");
                sanseviera.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246580/bvnmu1eu587sd4cfkiqp.mp4");

                Producto crassula = new Producto();
                crassula.setCategoria(suculenta.get());
                crassula.setNombre("Creassula");
                crassula.setDescripcion("Maceta soplada N°12");
                crassula.setPrecio(1600.0);
                crassula.setStock(9);
                crassula.setCuidado("Plantas suculentas con follaje llamativo de color verde y rojo brillante. La coloración rojiza se obtiene gracias a la exposición prolongada al sol directo. Prefiere luz solar directa, sin embargo, bajo temperaturas demasiado altas sus hojas pueden sufrir quemaduras. Requieren suelos con buen drenaje y riegos moderados, permitiendo que el sustrato se seque completamente entre aplicaciones.");
                crassula.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620423/crassula_-_maceta_soplada_n12_-_1600_njcgd7.jpg");
                crassula.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246705/opa2fah9clphoekbavks.mp4");

                Producto haworthia = new Producto();
                haworthia.setCategoria(suculenta.get());
                haworthia.setNombre("Haworthia Cymbiformis");
                haworthia.setDescripcion("Maceta N°12");
                haworthia.setPrecio(2300.0);
                haworthia.setStock(5);
                haworthia.setCuidado("Muy atractiva por sus hojas translúcidas dispuestas en forma de roseta. Es de fácil cuidado. Necesita luz indirecta y suelos bien drenados. Riegos moderados, dejando secar el sustrato entre aplicaciones.");
                haworthia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620541/haworthia_cymbiformis-_maceta_n12-_2300_zmk42j.jpg");
                haworthia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246927/f8vanob25g4nzlbgllrt.mp4");

                Producto kalanchoe = new Producto();
                kalanchoe.setCategoria(suculenta.get());
                kalanchoe.setNombre("Kalanchoe Humilis");
                kalanchoe.setDescripcion("Maceta soplada N°12");
                kalanchoe.setPrecio(1500.0);
                kalanchoe.setStock(9);
                kalanchoe.setCuidado("Destaca por su follaje atigrado en tonos verdes, grises y rojizos. Es tóxica para mascotas. Prefiere luz solar plena y soporta elevadas temperaturas. Prefiere suelos con buen drenaje pero no es exigente en nutrientes. Riegos moderados, evitando el exceso de humedad que puede causar pudrición. Resiste muy bien la sequía.");
                kalanchoe.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620589/kalanchoe_humilis._maceta_soplada_n12-_1500_cnaz9d.jpg");
                kalanchoe.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749247052/lgb5dmgeeygeipzfjbkt.mp4");

                productoService.guardar(bracho);
                productoService.guardar(chivato);
                productoService.guardar(guayacan);
                productoService.guardar(thevetia);
                productoService.guardar(boldo);
                productoService.guardar(incienso);
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
                productoService.guardar(haworthia);
                productoService.guardar(kalanchoe);

                System.out.println("Productos cargados");
            }
        };
    }

}
