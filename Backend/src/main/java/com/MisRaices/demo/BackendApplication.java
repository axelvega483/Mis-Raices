package com.MisRaices.demo;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.service.CategoriaService;
import com.MisRaices.demo.service.ProductoService;
import com.MisRaices.demo.util.ExposicionProducto;
import com.MisRaices.demo.util.OrigenProducto;
import com.MisRaices.demo.util.TamañoProducto;
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
                bracho.setDescripcion("🧬 <b><i>Brachychiton populneus</i></b>\n\n"
                        + "🌱 Plantines de 50 cm de alto en bolsín de 1 L.\n"
                        + "💧 Riego moderado.\n"
                        + "❄️ Resistente a la sequía y a heladas débiles.\n"
                        + "🏡 Ideal para jardines y paseos.");
                bracho.setPrecio(1000.0);
                bracho.setExposicion(ExposicionProducto.sol_pleno);
                bracho.setOrigen(OrigenProducto.Exotica);
                bracho.setTamanio(TamañoProducto.Grande);
                bracho.setStock(8);
                bracho.setCuidado("☀️ Requiere exposición a pleno sol y suelos bien drenados.\n"
                        + " ❄️ Es resistente a la sequía una vez establecido, pero se beneficia de \n"
                        + "💧 riegos regulares durante períodos secos. Puede soportar heladas débiles siendo un árbol jóven.");
                bracho.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743618906/bracho-40cm-bolsa_1l-_1000_tsglxb.jpg");
                bracho.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749244040/kiammsez6zee363cuh2l.mp4");

                Producto chivato = new Producto();
                chivato.setCategoria(arbol.get());
                chivato.setNombre("Chivato");
                chivato.setDescripcion("🧬 <b><i>Delonix regia</i></b>\n\n"
                        + "🌱 Plantines de 50 cm de alto en bolsín de 5 L.\n"
                        + " ❄️ Sensible a heladas.\n"
                        + "🌿 Ideal para sitios amplios.\n"
                        + " ⚠️ Posee sistema radical agresivo.");
                chivato.setPrecio(3000.0);
                chivato.setExposicion(ExposicionProducto.sol_pleno);
                chivato.setTamanio(TamañoProducto.Grande);
                chivato.setOrigen(OrigenProducto.Exotica);
                chivato.setStock(7);
                chivato.setCuidado("☀️ Prefiere climas cálidos y soleados.\n"
                        + "Necesita suelos bien drenados y 💧 riegos moderados. \n"
                        + "🌺 Planta muy apreciada en jardinería por su espectacular floración de color rojo intenso.\n"
                        + "Se debe plantar en sitios amplios, sin construcciones ya que posee un sistema radicular agresivo.\n"
                        + "❄️ Sensible al frío, no soporta heladas intensas y prolongadas.");
                chivato.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743618964/chivato-bolsa_5l-_3000_lyfoep.jpg");
                chivato.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749244824/a2sgisjjczoi3tj036dv.mp4");

                Producto guayacan = new Producto();
                guayacan.setCategoria(arbol.get());
                guayacan.setNombre("Guayacan");
                guayacan.setDescripcion("🧬 <b><i>Libidibia paraguariensis</i></b>\n\n"
                        + "🌱 Plantines de 80 cm de alto en bolsín de 5 L.\n"
                        + "🌲 Corteza muy llamativa en tonos gris-verdosos.\n"
                        + "💧 Riego moderado.\n"
                        + "❄️ Resistente a la sequía.\n"
                        + "🏡 Ideal para jardines y parquizados.");
                guayacan.setPrecio(1800.0);
                guayacan.setExposicion(ExposicionProducto.sol_pleno);
                guayacan.setTamanio(TamañoProducto.Grande);
                guayacan.setOrigen(OrigenProducto.Nativa);
                guayacan.setStock(5);
                guayacan.setCuidado("🌲 Se destaca por su corteza delgada constituida por escamas pardo-verdosas que se desprenden dejando parches de color verde-grisáceos.\n"
                        + "☀️ Prospera en pleno sol y suelos bien drenados.\n"
                        + "❄️ Tolera la sequía gracias a su sistema radicular profundo.\n"
                        + "💧 Riego regular en su etapa de crecimiento.\n"
                        + "🏡 Ideal para jardines amplios o parquizados.");
                guayacan.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619016/Guayac%C3%A1n-bolsa_5l-_80cm-_1800_os7byl.jpg");
                guayacan.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749244936/zstixerge0c0zsd1acje.mp4");

                Producto thevetia = new Producto();
                thevetia.setCategoria(arbol.get());
                thevetia.setNombre("Thevetia");
                thevetia.setDescripcion("🧬 <b><i>Thevetia peruviana</i></b>\n\n"
                        + "🌱 Plantines de 1 m de alto en recipiente descartable.\n"
                        + "❄️ Resistente a la sequía.\n"
                        + "🏡 Ideal para jardines y veredas pequeñas.\n"
                        + "⚠️ Posee látex tóxico.");
                thevetia.setPrecio(2800.0);
                thevetia.setExposicion(ExposicionProducto.sol_pleno);
                thevetia.setTamanio(TamañoProducto.Grande);
                thevetia.setOrigen(OrigenProducto.Nativa);
                thevetia.setStock(9);
                thevetia.setCuidado("☀️ Necesita exposición a pleno sol y suelos bien drenados.\n"
                        + "❄️ Es resistente a la sequía y florece durante gran parte del año con flores amarillas llamativas.\n"
                        + "⚠️ No soporta heladas intensas y prolongadas.\n"
                        + "⚠️ Posee látex tóxico para personas y mascotas, se recomienda lavado con agua y jabón en caso de contacto con la piel.");
                thevetia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619047/thevetia-_recipiente_descartable-_1m-_2800_oxevqi.jpg");
                thevetia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245049/ubuylxsvswxp98hs2yhu.mp4");

                Producto boldo = new Producto();
                boldo.setCategoria(aromaticas.get());
                boldo.setNombre("Boldo Paraguayo");
                boldo.setDescripcion("🧬 <b><i>Plectranthus barbatus</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 12.\n"
                        + "🌿 Planta rastrera de follaje perenne. Muy aromática.\n"
                        + "💧 riego moderado.\n"
                        + "🏡 Ideal para macetas y canteros.\n"
                        + "⚠️ Posee compuestos tóxicos.");
                boldo.setPrecio(1200.0);
                boldo.setExposicion(ExposicionProducto.sol_pleno);
                boldo.setTamanio(TamañoProducto.Mediano);
                boldo.setOrigen(OrigenProducto.Exotica);
                boldo.setStock(5);
                boldo.setCuidado("🌤️ Prefiere sol suave o media sombra y suelos sueltos que drenen bien.\n"
                        + "💧 No requiere riegos constantes; mantener el sustrato apenas húmedo es suficiente.\n"
                        + "🏡 Puede cultivarse en maceta o jardín, y la poda ayuda a mantener su forma.\n"
                        + "⚠️ No se recomienda su consumo ya que posee compuestos tóxicos.");
                boldo.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619143/boldo_paraguayo-_maceta_n12-_1200_w1ady2.jpg");
                boldo.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245155/ezcshaceba54pzjpazm0.mp4");

                Producto incienso = new Producto();
                incienso.setCategoria(aromaticas.get());
                incienso.setNombre("Incienso");
                incienso.setDescripcion("🧬 <b><i>Plectranthus coleoides</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 12.\n"
                        + "🌿 Planta rastrera de follaje perenne, variegado. Muy aromática.\n"
                        + "⚠️ Sensible al exceso de riego.\n"
                        + "🏡 Ideal para macetas y canteros.");
                incienso.setPrecio(1500.0);
                incienso.setExposicion(ExposicionProducto.luz_indirecta);
                incienso.setTamanio(TamañoProducto.Mediano);
                incienso.setOrigen(OrigenProducto.Nativa);
                incienso.setStock(10);
                incienso.setCuidado("🌤️ Requiere luz indirecta y suelos bien drenados.\n"
                        + "⚠️ No soporta temperaturas demasiado altas o bajas, ideal para climas templados.\n"
                        + "💧 Es sensible al exceso de riego, por lo que se debe permitir que el sustrato se seque entre riegos.");
                incienso.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619185/incienso-_maceta_n12-_1500_d2srrf.jpg");
                incienso.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245236/t70vnwlqrztzmgaptl5t.mp4");

                Producto mammillaria = new Producto();
                mammillaria.setCategoria(cactus.get());
                mammillaria.setNombre("Mammillaria Elongata");
                mammillaria.setDescripcion("🧬 <b><i>Mammillaria elongata</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 6.\n"
                        + "🌵 Cactus pequeños de porte rastrero.\n"
                        + "⚠️ Es sensible al exceso de riego.\n"
                        + "🏡 Ideal para macetas pequeñas.");
                mammillaria.setPrecio(500.0);
                mammillaria.setExposicion(ExposicionProducto.sol_pleno);
                mammillaria.setTamanio(TamañoProducto.Pequenio);
                mammillaria.setOrigen(OrigenProducto.Exotica);
                mammillaria.setStock(15);
                mammillaria.setCuidado("🌞 Necesita mucha luz solar directa y riegos muy espaciados, especialmente en invierno.\n"
                        + "Se recomienda utilizar sustrato bien drenado.\n"
                        + "🏡 Ideal para macetas pequeñas.");
                mammillaria.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619340/mammillaria_elongata-_maceta_n6-_500_w3nj5f.jpg");
                mammillaria.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245332/hbbpftcyxo0vukxvtou3.mp4");

                Producto gracilis = new Producto();
                gracilis.setCategoria(cactus.get());
                gracilis.setNombre("Mammillaria Gracilis");
                gracilis.setDescripcion("🧬 <b><i>Mammillaria gracilis</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 8.\n"
                        + "🌵 Cactus pequeños de porte cespitoso.\n"
                        + "⚠️ Es sensible al exceso de riego.\n"
                        + "🏡 Ideal para macetas pequeñas.");
                gracilis.setPrecio(700.0);
                gracilis.setExposicion(ExposicionProducto.sol_pleno);
                gracilis.setTamanio(TamañoProducto.Pequenio);
                gracilis.setOrigen(OrigenProducto.Exotica);
                gracilis.setStock(13);
                gracilis.setCuidado("☀️ Prefiere exposición solar directa y altas temperaturas.\n"
                        + "💧 Necesita riegos moderados y suelos con buen drenaje.\n"
                        + "Dejar que el sustrato se seque completamente entre riegos para evitar pudrición de raíces.");
                gracilis.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619393/mammillaria_gracilis-_maceta_n8-_700_taopo0.jpg");
                gracilis.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245399/gr7xe9hprzfr2iqbazyl.mp4");

                Producto plumosa = new Producto();
                plumosa.setCategoria(cactus.get());
                plumosa.setNombre("Mammillaria Plumosa");
                plumosa.setDescripcion("🧬 <b><i>Mammillaria plumosa</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 6.\n"
                        + "🌵 Cactus pequeños de forma globosa de aspecto suave.\n"
                        + "⚠️ Es sensible al exceso de riego.\n"
                        + "🏡 Ideal para macetas pequeñas.");
                plumosa.setPrecio(500.0);
                plumosa.setExposicion(ExposicionProducto.sol_pleno);
                plumosa.setTamanio(TamañoProducto.Pequenio);
                plumosa.setOrigen(OrigenProducto.Exotica);
                plumosa.setStock(8);
                plumosa.setCuidado("☀️ Requiere sol pleno.\n"
                        + "🌿 Prospera en suelos bien drenados con riegos escasos ya que es tolerante a la sequía y muy sensible al exceso de agua.");
                plumosa.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619439/mammillaria_plumosa-_maceta_n6-_500_lbgxtf.jpg");
                plumosa.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245486/oe78ndawrtlneodkhelp.mp4");

                Producto prolifera = new Producto();
                prolifera.setCategoria(cactus.get());
                prolifera.setNombre("Mammillaria Prolifera");
                prolifera.setDescripcion("🧬 <b><i>Mammillaria prolifera</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 8.\n"
                        + "🌵 Cactus pequeños de porte rastrero.\n"
                        + "⚠️ Es sensible al exceso de riego.\n"
                        + "🏡 Ideal para macetas pequeñas.");
                prolifera.setPrecio(700.0);
                prolifera.setExposicion(ExposicionProducto.sol_pleno);
                prolifera.setTamanio(TamañoProducto.Pequenio);
                prolifera.setOrigen(OrigenProducto.Exotica);
                prolifera.setStock(7);
                prolifera.setCuidado("☀️ Necesita exposición solar directa y riegos moderados a escasos.\n"
                        + "💧 Es importante evitar el encharcamiento para prevenir enfermedades radiculares.");
                prolifera.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619494/mammillaria_prolifera-_maceta_n8-_700_cfke52.jpg");
                prolifera.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245676/hshfoawazuiy0qzildub.mp4");

                Producto opuntia = new Producto();
                opuntia.setCategoria(cactus.get());
                opuntia.setNombre("Opuntia Humifusa");
                opuntia.setDescripcion("🧬 <b><i>Opuntia humifusa</i></b>\n\n"
                        + "🌱 Plantines en maceta soplada N° 12.\n"
                        + "🌵 Cactus de tallos aplanados y porte rastrero.\n"
                        + "🔥 Soporta altas temperaturas.\n"
                        + "🏡 Ideal para canteros y macetas.");
                opuntia.setPrecio(700.0);
                opuntia.setExposicion(ExposicionProducto.sol_pleno);
                opuntia.setTamanio(TamañoProducto.Mediano);
                opuntia.setOrigen(OrigenProducto.Exotica);
                opuntia.setStock(8);
                opuntia.setCuidado("☀️ Prefiere pleno sol y suelos con buen drenaje.\n"
                        + "💧 Riegos esporádicos, permitiendo que el sustrato se seque completamente entre aplicaciones.\n"
                        + "🔥 Soporta perfectamente altas temperaturas.");
                opuntia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619530/opuntia_humifusa-_maceta_soplada_n12-_700_ylpa71.jpg");
                opuntia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245788/atepfk80j3iphoflfilo.mp4");

                Producto albuca = new Producto();
                albuca.setCategoria(exterior.get());
                albuca.setNombre("Albuca Bracteata");
                albuca.setDescripcion("🧬 <b><i>Albuca bracteata</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 18.\n"
                        + "🌿 Planta de tallo bulboso y follaje perenne.\n"
                        + "⚠️ Es sensible a las altas temperaturas.\n"
                        + "🏡 Ideal para macetas y canteros.\n"
                        + "☠️ Su ingesta es tóxica.");
                albuca.setPrecio(3500.0);
                albuca.setExposicion(ExposicionProducto.sol_pleno);
                albuca.setTamanio(TamañoProducto.Mediano);
                albuca.setOrigen(OrigenProducto.Exotica);
                albuca.setStock(5);
                albuca.setCuidado("☠️ Tóxica para mascotas y humanos, no se recomienda su ingesta.\n"
                        + "☀️ Soporta sol directo pero no altas temperaturas para evitar quemaduras en hojas.\n"
                        + "💧 Necesita suelo bien drenado y riegos moderados, evitando exceso de humedad que provoque pudrición del bulbo.");
                albuca.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619756/albuca_bracteata-_maceta_n18-_3500_d0udxv.jpg");
                albuca.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245892/npwd5ksil3y8jzcpm4dm.mp4");

                Producto euphorbia = new Producto();
                euphorbia.setCategoria(exterior.get());
                euphorbia.setNombre("Euphorbia Umbellata");
                euphorbia.setDescripcion("🧬 <b><i>Euphorbia umbellata</i></b>\n\n"
                        + "🌱 Plantines en maceta soplada N° 16.\n"
                        + "🌿 Arbusto perenne de 6 a 8 m de altura, follaje colorido.\n"
                        + "❄️ Es sensible al frío.\n"
                        + "🏡 Ideal para jardines y canteros.\n"
                        + "☠️ Posee látex tóxico.");
                euphorbia.setPrecio(2100.0);
                euphorbia.setExposicion(ExposicionProducto.luz_indirecta);
                euphorbia.setTamanio(TamañoProducto.Grande);
                euphorbia.setOrigen(OrigenProducto.Nativa);
                euphorbia.setStock(4);
                euphorbia.setCuidado("☀️ Prefiere luz indirecta brillante y suelos bien drenados.\n"
                        + "💧 Riegos moderados, dejando que el sustrato se seque entre aplicaciones.\n"
                        + "❄️ Muy sensible al frío, ideal para climas templados.");
                euphorbia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619853/euphorbia_umbellata-_maceta_soplada_n16-_2100_gzwrte.jpg");
                euphorbia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749245994/wk2cgdfwb2vwrnehu6yy.mp4");

                Producto rayito = new Producto();
                rayito.setCategoria(exterior.get());
                rayito.setNombre("Rayito de Sol");
                rayito.setDescripcion("🧬 <b><i>Lampranthus multiradiatus</i></b>\n\n"
                        + "🌱 Plantines en maceta bols N° 18.\n"
                        + "🌿 Planta de porte rastrero, hojas suculentas.\n"
                        + "🌸 Floración llamativa.\n"
                        + "🏡 Ideal para macetas colgantes.");
                rayito.setPrecio(2800.0);
                rayito.setExposicion(ExposicionProducto.sol_pleno);
                rayito.setTamanio(TamañoProducto.Mediano);
                rayito.setOrigen(OrigenProducto.Exotica);
                rayito.setStock(4);
                rayito.setCuidado("☀️ Prefiere pleno sol con al menos 6 horas diarias de luz.\n"
                        + "💧 Riegos moderados y sustrato liviano."
                        + "🌡️ Prefiere climas cálidos.");
                rayito.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743619961/Rayito_de_sol-_maceta_bolsa_n18_colgante-_2800_j2nuky.jpg");
                rayito.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246094/zkn5nucfilorox72b0n5.mp4");

                Producto tradescantia = new Producto();
                tradescantia.setCategoria(exterior.get());
                tradescantia.setNombre("Tradescantia Pallida");
                tradescantia.setDescripcion("🧬 <b><i>Tradescantia pallida</i></b>\n\n"
                        + "🌱 Plantines en bolsín de 5 L.\n"
                        + "🌿 Planta de porte rastrero, hojas de color púrpura.\n"
                        + "🌡️ Prefiere climas cálidos.\n"
                        + "🏡 Ideal para macetas y canteros.");
                tradescantia.setPrecio(2300.0);
                tradescantia.setExposicion(ExposicionProducto.luz_indirecta);
                tradescantia.setTamanio(TamañoProducto.Mediano);
                tradescantia.setOrigen(OrigenProducto.Exotica);
                tradescantia.setStock(6);
                tradescantia.setCuidado("🚫 Evitar luz solar directa.\n"
                        + "🌱 Prefiere suelos bien drenados.\n"
                        + "💧 Riegos moderados, permitiendo que el sustrato se seque entre aplicaciones.\n"
                        + "🌡️ Prefiere climas cálidos.");
                tradescantia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620079/tradescantia_pallida-bolsa_5l-_2300_yzytcp.jpg");
                tradescantia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246173/ypuuney1kwqxtrgi02cg.mp4");

                Producto helecho = new Producto();
                helecho.setCategoria(interior.get());
                helecho.setNombre("Helecho Serrucho");
                helecho.setDescripcion("🧬 <b><i>Nephrolepis cordifolia</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 15.\n"
                        + "🌿 Planta de porte robusto, muy frondosa.\n"
                        + "🏡 Ideal para macetas grandes.");
                helecho.setPrecio(400.0);
                helecho.setExposicion(ExposicionProducto.luz_indirecta);
                helecho.setTamanio(TamañoProducto.Mediano);
                helecho.setOrigen(OrigenProducto.Nativa);
                helecho.setStock(4);
                helecho.setCuidado("🌿 Prefiere luz solar indirecta y ambientes húmedos.\n"
                        + "💧 Mantener el sustrato húmedo, pero sin encharcar.\n"
                        + "🚫 Evitar exposición a aire acondicionado directo, calefacción u otros artefactos que resequen el ambiente.");
                helecho.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620139/helecho_serrucho-_maceta_n15-_4000_eol1jv.jpg");
                helecho.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246247/vk8xbcr1pw8un9llbkuv.mp4");

                Producto brasil = new Producto();
                brasil.setCategoria(interior.get());
                brasil.setNombre("Philodendron Brasil");
                brasil.setDescripcion("🧬 <b><i>Philodendron hederaceum</i></b>\n\n"
                        + "🌱 Plantines en maceta soplada N° 12.\n"
                        + "🌿 Planta de porte trepador con follaje llamativo por su patrón de colores.\n"
                        + "🏡 Ideal para macetas colgantes.\n"
                        + "⚠️ Tóxica para mascotas.");
                brasil.setPrecio(2000.0);
                brasil.setExposicion(ExposicionProducto.luz_indirecta);
                brasil.setTamanio(TamañoProducto.Pequenio);
                brasil.setOrigen(OrigenProducto.Nativa);
                brasil.setStock(6);
                brasil.setCuidado("⚠️ Tóxica para mascotas.\n"
                        + "🌞 Crece bien con mucha luz indirecta y humedad ambiental media.\n"
                        + "💧 Requiere riegos regulares, dejando secar la capa superior del sustrato entre cada uno.\n"
                        + "🚫 Es importante no exponer la planta a aire acondicionado directo, calefacción u otro artefacto que pueda resecar demasiado el ambiente.");
                brasil.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620217/philodendron_brasil-_maceta_soplada_n12-_2000_jcmt0w.jpg");
                brasil.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246310/xvmszxtcc8ozfiryily5.mp4");

                Producto plateado = new Producto();
                plateado.setCategoria(interior.get());
                plateado.setNombre("Philodendron Plateado");
                plateado.setDescripcion("🧬 <b><i>Philodendron hastatum</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 14.\n"
                        + "🌿 Planta de porte trepador con follaje llamativo por su tonalidad plateada.\n"
                        + "🏡 Ideal para macetas colgantes.");
                plateado.setPrecio(3000.0);
                plateado.setExposicion(ExposicionProducto.luz_indirecta);
                plateado.setTamanio(TamañoProducto.Pequenio);
                plateado.setOrigen(OrigenProducto.Exotica);
                plateado.setStock(3);
                plateado.setCuidado("⚠️ Tóxica para mascotas. 🌞 Necesita luz solar indirecta y suelos bien drenados.\n"
                        + "💧 Riegos moderados, evitando el encharcamiento.\n"
                        + "🚫 Es importante no exponer la planta a aire acondicionado directo, calefacción u otro artefacto que pueda resecar demasiado el ambiente.");
                plateado.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620289/philodendron_plateado-maceta_n14-_3000_whoxet.jpg");
                plateado.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246493/dcdcfkht7nncwl8daxg5.mp4");

                Producto sanseviera = new Producto();
                sanseviera.setCategoria(interior.get());
                sanseviera.setNombre("Sansevieria Enana");
                sanseviera.setDescripcion("🧬 <b><i>Dracaena trifasciata 'Hahnii'</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 15.\n"
                        + "🌿 Planta de interior con follaje suculento.\n"
                        + "🏡 Ideal para macetas medianas.");
                sanseviera.setPrecio(2800.0);
                sanseviera.setExposicion(ExposicionProducto.luz_indirecta);
                sanseviera.setTamanio(TamañoProducto.Mediano);
                sanseviera.setOrigen(OrigenProducto.Exotica);
                sanseviera.setStock(7);
                sanseviera.setCuidado("🌬️ Muy utilizada por su capacidad para purificar el aire.\n"
                        + "☀️ Tolera una amplia gama de condiciones de luz, aunque la exposición directa al sol prolongada puede quemar sus hojas.\n"
                        + "💧 Requiere riegos esporádicos y suelo bien drenado.");
                sanseviera.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620336/Sansevieria_enana-_maceta_n15-_2800_m7qdqi.jpg");
                sanseviera.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246580/bvnmu1eu587sd4cfkiqp.mp4");

                Producto crassula = new Producto();
                crassula.setCategoria(suculenta.get());
                crassula.setNombre("Creassula");
                crassula.setDescripcion("🧬 <b><i>Crassula capitella</i></b>\n\n"
                        + "🌱 Plantines en maceta soplada N° 12.\n"
                        + "🌿 Planta suculenta de follaje llamativo color rojo.\n"
                        + "🏡 Ideal para macetas medianas.");
                crassula.setPrecio(1600.0);
                crassula.setExposicion(ExposicionProducto.sol_pleno);
                crassula.setTamanio(TamañoProducto.Pequenio);
                crassula.setOrigen(OrigenProducto.Exotica);
                crassula.setStock(9);
                crassula.setCuidado("🌞 La coloración rojiza se obtiene gracias a la exposición prolongada al sol directo.\n"
                        + "⚠️ Prefiere luz solar directa, pero bajo temperaturas muy altas sus hojas pueden sufrir quemaduras.\n"
                        + "💧 Requiere suelos con buen drenaje y riegos moderados, dejando secar el sustrato entre riegos.");
                crassula.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620423/crassula_-_maceta_soplada_n12_-_1600_njcgd7.jpg");
                crassula.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246705/opa2fah9clphoekbavks.mp4");

                Producto haworthia = new Producto();
                haworthia.setCategoria(suculenta.get());
                haworthia.setNombre("Haworthia Cymbiformis");
                haworthia.setDescripcion("🧬 <b><i>Haworthia cymbiformis</i></b>\n\n"
                        + "🌱 Plantines en maceta N° 12.\n"
                        + "🌿 Planta suculenta de follaje translúcido, en forma de roseta.\n"
                        + "🏡 Ideal para macetas pequeñas.");
                haworthia.setPrecio(2300.0);
                haworthia.setExposicion(ExposicionProducto.luz_indirecta);
                haworthia.setTamanio(TamañoProducto.Pequenio);
                haworthia.setOrigen(OrigenProducto.Exotica);
                haworthia.setStock(5);
                haworthia.setCuidado("☀ Necesita luz indirecta y suelos bien drenados.\n"
                        + "💧 Riegos moderados, dejando secar el sustrato entre aplicaciones.");
                haworthia.setImg("https://res.cloudinary.com/dhaot8eju/image/upload/v1743620541/haworthia_cymbiformis-_maceta_n12-_2300_zmk42j.jpg");
                haworthia.setVideo("https://res.cloudinary.com/dhaot8eju/video/upload/v1749246927/f8vanob25g4nzlbgllrt.mp4");

                Producto kalanchoe = new Producto();
                kalanchoe.setCategoria(suculenta.get());
                kalanchoe.setNombre("Kalanchoe Humilis");
                kalanchoe.setDescripcion("🧬 <b><i>Kalanchoe humilis</i></b>\n\n"
                        + "🌱 Plantines en maceta soplada N° 12.\n"
                        + "🌿 Planta suculenta de follaje atigrado en tonos rosados, verdes y blancos.\n"
                        + "🏡 Ideal para macetas medianas.\n"
                        + "⚠️ Tóxico para mascotas.");
                kalanchoe.setPrecio(1500.0);
                kalanchoe.setExposicion(ExposicionProducto.sol_pleno);
                kalanchoe.setTamanio(TamañoProducto.Mediano);
                kalanchoe.setOrigen(OrigenProducto.Exotica);
                kalanchoe.setStock(9);
                kalanchoe.setCuidado("⚠️ Es tóxica para mascotas.\n"
                        + "☀️ Prefiere luz solar plena y soporta elevadas temperaturas.\n"
                        + "🌵 Prefiere suelos con buen drenaje pero no es exigente en nutrientes.\n"
                        + "💧 Riegos moderados, evitando el exceso de humedad que puede causar pudrición.\n"
                        + "🌞 Resiste muy bien la sequía.");
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
