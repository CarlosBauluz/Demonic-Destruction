# Introducción

Demonic-Destruction es un trepidante juego de acción y supervivencia con una vibrante mezcla de elementos roguelite. En este emocionante universo, los jugadores enfrentan la invasión demoníaca, combatiendo implacables hordas de enemigos en un desafío de resistencia y astucia.

# Características Principales

- Combate Dinámico: Enfréntate a una variedad de demonios, cada uno con sus propias habilidades y tácticas. A medida que avanzas, los enemigos derrotados dejarán caer ítems que podrás recoger y utilizar para mejorar tus habilidades de supervivencia.
- Mecánicas Roguelite: Experimenta la intensidad de las hordas aleatorias de enemigos en cada partida, asegurando una experiencia única y desafiante en cada intento.
- Controles Intuitivos: Navega por el mundo del juego utilizando las teclas A (Izquierda), S (Abajo), D (Derecha) y W (Arriba). Lucha contra los enemigos con ataques interactivos.
- Estética Artística: Sumérgete en un entorno inicialmente renderizado en blanco y negro, con posibilidades de expansión a colores basados en la recepción y comentarios de los jugadores.
- Exploración de Mapas: Explora un mapa expansivo y único, diseñado para ofrecer libertad de movimiento y encuentros constantes con hordas de enemigos.
- Interfaz Usuario Pixel Art: Disfruta de una interfaz de usuario intuitiva y atractiva con un diseño clásico de pixel art.
- Sistema de Seguimiento: Mantén un registro de tu tiempo de supervivencia y estadísticas de los enemigos, como tipo, salud y categoría, a través de un sistema de contador y métodos de visualización eficientes.
- Sistema de Movimiento del Personaje: Dirige tu personaje a tu gusto con A W S D como vectores de dirección.

- Opciones del Menú Principal:
  
  - Iniciar Partida: Comienza tu aventura y desafía las hordas demoníacas.

  - Cerrar Juego: Sal del juego cuando necesites un respiro de la acción.
 
 # Imagen del juego Iniciado

 <img width="990" alt="Captura de pantalla 2023-12-04 a las 8 49 13" src="https://github.com/CarlosBauluz/Demonic-Destruction/assets/150656730/7b03cd01-5d5d-4063-b406-d38001f310c9">

 - Imagen donde se ve la generación de monstruos de forma aleatoria

  # Como jugar

  El movimiento se basa en el uso de las teclas awsd. Cuando comenzamos partida nos podemos encontrar que tenderemos un personaje en el centro de la pantalla. Ese es nuestro jugador.

  - Arriba tenemos el tiempo que llevamos vivos y debajo una barrita que indica la vida que tenemos.

  - Abajo a la izquierda tenmos el cooldown de nuestro escudo, y abajo derecha tenemos el cooldown de nuestro ataque masivo.

Para atacar no tenemos que hacer nada, el ataque es automatico. El jugador ataca automaticamente en intervalos de dos segundos hacia la ultima direccion que miraba. Si le damos a la tecla "q", podemos ver que se nos aplicara un escudo durante un breve periodo de tiempo. Si pulsamos la tecla "e", haremos una ataque en area.

El juego se basa en sobrebibir. Poco a poco te van a ir apareciendo enemigos, los cuales tienen comom objetivo ir a por el jugador. El objetivo es sobrebibir y matar al maximo de enemigos que se pueda.

Para facilitar un poco la supervivencia, podemos encontrar PowerUps y botiquines. Los PowerUps de velocidad son los que tienen el color azul, PowerUps de daño son los que tienen el color verde, y los botiquines tienen el color rojo.

# Tipos de enemigos:

Vampiro:

![vampiro](https://github.com/CarlosBauluz/Demonic-Destruction/assets/150656730/f31d8a82-3fc9-40fa-82c0-aca325c9c124)

No muerto:
![verde](https://github.com/CarlosBauluz/Demonic-Destruction/assets/150656730/50b46961-dd52-45cc-ad95-54b0ea510c7d)

Lobo:
![lobo](https://github.com/CarlosBauluz/Demonic-Destruction/assets/150656730/2e6a4791-633a-484b-8f7a-8b918841b61e)

Demonio:
![rojo](https://github.com/CarlosBauluz/Demonic-Destruction/assets/150656730/a24155be-c67e-487d-ab16-e607e4928eb8)


  # Funccionamiento del codigo

  - Clase Jugador
    - Representa al jugador en el juego.
    - Mantiene propiedades como health (salud), damage (daño), posición (x, y), y estado de vida (jugadorVivo).
    - Incluye métodos para mover al jugador, atacar a los enemigos, recibir daño y verificar la invulnerabilidad.
    - Gestiona el cooldown de habilidades especiales como el escudo y el ataque masivo.
  - Clase Monstruo
    - Representa a los enemigos en el juego.
    - Cada monstruo tiene atributos como salud, daño, tipo y posición.
    - Incluye métodos para recibir daño y morir, así como para actualizar su posición en el mapa.
  - Clase Oleadas
    - Gestiona la generación y manejo de oleadas de enemigos.
    - Controla la cantidad y tipo de enemigos que aparecen en cada oleada.
    - Incluye métodos para agregar y eliminar enemigos, así como para generar nuevos enemigos cerca del jugador.
  - Clase Ventanajuego
    - Es el núcleo del juego, donde se maneja la lógica principal y la interfaz gráfica.
    - Gestiona la creación y actualización de todos los elementos del juego, incluyendo el jugador, enemigos, power-ups y el mapa.
    - Incluye un bucle principal (timer) que actualiza constantemente el estado del juego.
    - Maneja la interacción del usuario a través de eventos de teclado.
  - Clases PowerUpDanio y PowerUpVelocidad
    - Representan power-ups (mejoras) que el jugador puede recoger en el juego.
    - Afectan temporalmente las habilidades del jugador, como aumentar el daño o la velocidad.
  - Clase Curacion
    - Representa ítems de curación que el jugador puede recoger para restaurar salud.
  - Clase FinJuegoVentana
    - Se activa cuando el jugador muere, mostrando estadísticas como el tiempo sobrevivido y los enemigos enfrentados.
    - Permite reiniciar el juego o salir.
  - Funcionamiento General
    - Al iniciar el juego, se crea una instancia de Ventanajuego, que a su vez inicializa el jugador, los enemigos, y otros elementos.
    - El juego se actualiza en intervalos regulares mediante un timer, que controla el movimiento de los enemigos, la detección de colisiones, y las actualizaciones de estado.
    - El jugador se mueve por el mapa y combate enemigos usando controles de teclado.
    - A medida que el jugador avanza, se enfrenta a oleadas más difíciles y puede recoger power-ups y curaciones para ayudar en su supervivencia.
  - Interacción y Gráficos
    - La interacción del usuario se maneja principalmente a través de eventos de teclado.
    - La interfaz gráfica utiliza Swing y se actualiza constantemente para reflejar el estado actual del juego.

 
 # Diagrama UML

![Diagrama_sin_titulo drawio](https://github.com/CarlosBauluz/Demonic-Destruction/assets/145339678/124293e3-f1db-4ed5-9937-ba65b6b4bcfa)

