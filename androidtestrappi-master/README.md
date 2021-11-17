# androidtestrappi

## Negocio
- **MovieDetailsResponse**: Objeto que representa la respuesta de la API de themoviedb para los detalles de una pelicula
- **MovieItemAdapter**: Adaptador del listview para los objetos de tipo Movie
- **MovieResponse**: Objeto que representa la respuesta de la API de themoviedb para los datos basicos de una pelicula
- **TvDetailsResponse**: Objeto que representa la respuesta de la API de themoviedb para los detalles de una Serie/Tv
- **TvItemAdapter**:  Adaptador del listview para los objetos de tipo Tv/Serie
- **TvResponse**: Objeto que representa la respuesta de la API de themoviedb para los datos basicos de una Tv/Serie
- **PlaceholderFragment**: Fragmento en donde se representan las categorías Popular, Top Rated, Upcoming de las Películas/Series
- **SectionsPagerAdapter**: Adaptador para mostrar las pestañas en el TabLayout

## Persistencia/Red
- **ApiRequest**: Se gestionan las peticiones a la API de themoviedb y la persistencia de datos en cache
- **GetServiceThemoviedb**: Interfaz con las definiciones de las rutas para la API 

## Vista
- **ActivityDetailsMovie**: Actividad para visualizar los detalles de las películas
- **ActivityDetailsTv**: Actividad para visualizar los detalles de las series
- **MainActivity**: Actividad principal en donde se dibujan los fragmentos

## Preguntas
1. **En qué consiste el principio de responsabilidad única? ¿Cuál es su propósito?**

Cada objeto debería tener una única responsabilidad o realizar una única cosa.
El propósito principal de este principio es hacer un código que sea flexible y escalable a la hora de hacer modificaciones

2. **Qué características tiene, según su opinión, ¿un “buen” código o código limpio?**
- Clases organizadas en paquetes
- No utilizar funciones descontinuadas
- Modularización de código para no generar código redundante
- Nombre de variables, métodos y clases con un significado
- Comentar funciones o clases poco intuitivas
