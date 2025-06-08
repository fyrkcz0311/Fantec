const app = document.getElementById('typerwriter');

const typewriter = new Typewriter(app, {
    loop: true,
    delay: 75
});

typewriter
  .typeString('La ciudad de las palmeras')
  .pauseFor(200)
  .start();
