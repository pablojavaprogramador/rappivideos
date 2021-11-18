# Rappi Videos

Este Proyecto ase implemento por capas utilizando el consumo de apis por medio de retrofit   -Persistencia -Vista  

Cada objeto tiene que  tener una única responsabilidad o realizar una única cosa.

## Negocio
MovieDetailsResponse.-Un Objeto que representa la respuesta de la API de themoviedb para los detalles de una pelicula

MovieItemAdapter .- Adaptador del listview para los objetos de tipo Pelicula

MovieResponse.-Un Objeto que representa la respuesta de la API de themoviedb para los datos basicos de una pelicula

TvDetailsResponse.-Un Objeto que representa la respuesta de la API de themoviedb para los detalles de una Serie

TvItemAdapter .-Un  Adaptador del listview para los objetos de tipo Serie

PlaceholderFragment.-  Fragmento en donde se representan las categorías Popular, Top Rated de las Películas/Series

SectionsPagerAdapter.- Adaptador para mostrar las pestañas en el TabLayout

## Persistencia/Red

#Local

FavoriteMovie.-Persistencia Local

MovieDao.-Dao del Proyecto

#Remote
ApiRequest.-Se gestionan las peticiones a la API de themoviedb y la persistencia de datos en cache

GetServiceThemoviedb.-Interfaz con las definiciones de las rutas verbos  para la API 

## Vista
ActivityDetallesPelicula .-Actividad para visualizar los detalles de las películas

ActivityDetallesTv .-Actividad para visualizar los detalles de las series

MainActivity .-Actividad principal en donde se dibujan los fragmentos

BusquedaActivity.-Actividad principal Para las Busquedas

Nombre del que solicita la vacante: Pablo Ernesto Cortes 

![alt text](https://raw.githubusercontent.com/pablojavaprogramador/rappivideos/main/APK/img.jpeg)