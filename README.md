
<div align="center">

# Document Indexer
</div>

<br>

### _indexador con actualización automática al agregar un archivo, selección del tipo de archivo a indexar, con interfaz gráfica._


<br><br>

Las técnicas de Indexación es un área de la Información Retrieval, 
existen muchas técnicas y la utilización de cada una depende de cada situación determinada.

<br><br><br>
<div align="center">
  <a href="https://github.com/alecio87/pilis-mod6-web-sistema-reciclaje">
    <img src="https://i.ibb.co/2gk4DfX/information-Retrieval.png" height="500">
  </a>
</div>

<br><br><br>
Los algoritmos de búsqueda se utilizan en muchas aplicaciones, pero existen algunas limitaciones en estos algoritmos. Cuando la base de datos es enorme o los documentos de texto son muy grande, la recuperación se ralentiza asiendo lenta la búsqueda. La solución a este problema es crear una estructura de datos (índice) para una búsqueda rápida. Existen
3
muchas técnicas de indexación, pero la mayoría de las técnicas familiares son archivos invertidos, matrices de sufijos y archivos de firma.

<br><br>

### Java
Esta aplicación fue desarrollada utilizando el lenguaje de programación Java que usa el paradigma de la programación Orientada a Objeto con ciertas características del paradigma funcional. Su sintaxis deriva en gran medida de C y C++, pero tiene menos utilidades de bajo nivel que cualquiera de ellos. Las aplicaciones de Java son compiladas a bytecode (clase Java), que puede ejecutarse en cualquier máquina virtual Java (JVM) sin importar la arquitectura de la computadora subyacente ni el Sistema Operativo local.
En esta ocasión se utilizó la versión Java Standard Edition 17 (Java SE 17), lanzada el 14 de septiembre de 2021.

<br><br>

### Lucene
Lucene es una librería usaba para construir una variedad de sistemas para la recuperación avanzada de información, siendo los Motores de Búsqueda la aplicación práctica más conocida, otras aplicaciones de Lucene son: Análisis de documentos, cargar y recorrer documentos de texto según algunos criterios determinados, encontrando términos específicos. Análisis de registros de aplicaciones de alto rendimiento. Búsqueda Geoespacial, con la reciente llegada de consultas y estructuras de datos geoespaciales, Lucene se está convirtiendo rápidamente en una opción popular para indexar datos de latitudinales y longitudinales y consultas como búsquedas de colocación.
Para este trabajo se utilizó la librería Lucene en su versión 9.10.0

<br><br>

### Eclipse
Para el trabajo de utilizo Eclipse que es un entorno de desarrollo Integrado para desarrollar software en este caso Java.
Además, es una plataforma de software compuesto por un conjunto de herramientas de programación de código abierto y multiplataformas.

<br><br>

### Maven
Para el proyecto también se utilizó Maven que es una herramienta de software para la gestión y construcción de proyectos.

<br><br>

### Estructura de las carpetas de la Aplicación
A continuación, mostramos la estructura de las carpetas donde están las Clases Java.

<br><br>
<div align="center">

  [![N|Solid](https://i.ibb.co/NNMqVC5/structure.png "project structure")](https://nodesource.com/products/nsolid)
</div>

<br><br>

### Cada una de las clases que componen la aplicación.

* La clase App.java es donde se encuentra el método main de java para arrancar la aplicación.
* La clase GUI.java contiene y configura la interfaz gráfica.
* La clase Index.java hace uso de todos los recursos que provee la librería Lucene.
* La clase ReadFile.java se encarga de extraer todo el texto de un archivo txt.
* La clase ReadPdf.java se encarga de extraer todo el texto de un archivo pdf.


