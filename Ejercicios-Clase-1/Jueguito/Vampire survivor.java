// Configuraciones iniciales
const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');
canvas.width = 800;
canvas.height = 600;

let player = {
    x: canvas.width / 2,
    y: canvas.height / 2,
    size: 20,
    speed: 5,
    weapon: null,
    level: 1,
    xp: 0,
    xpToLevelUp: 100,
    weaponLevel: 1
};

const weapons = [
    'Espada', 'Lanza', 'Arco', 'Hacha', 'Daga'
];

const enemies = [];
const projectiles = [];

const enemyTypes = ['Zombie', 'Esqueleto', 'Murciélago'];

// Elegir arma inicial al azar
function chooseRandomWeapon() {
    const randomIndex = Math.floor(Math.random() * weapons.length);
    return weapons[randomIndex];
}

player.weapon = chooseRandomWeapon();

// Sistema de nivel y elección de armas
const levelUpModal = document.getElementById('levelUpModal');
const weaponChoicesDiv = document.getElementById('weaponChoices');
const confirmChoiceButton = document.getElementById('confirmChoice');

// Mostrar opciones de armas al subir de nivel
function showWeaponChoices() {
    levelUpModal.style.display = 'block';
    weaponChoicesDiv.innerHTML = '';

    // Mostrar 5 opciones de armas
    const choices = [];
    while (choices.length < 5) {
        const weapon = weapons[Math.floor(Math.random() * weapons.length)];
        if (!choices.includes(weapon)) {
            choices.push(weapon);
        }
    }

    choices.forEach(weapon => {
        const button = document.createElement('button');
        button.textContent = weapon;
        button.onclick = () => {
            player.weapon = weapon;
            player.weaponLevel++;
            levelUpModal.style.display = 'none';
        };
        weaponChoicesDiv.appendChild(button);
    });
}

// Sistema de movimiento del jugador
function movePlayer() {
    if (keys['ArrowUp']) player.y -= player.speed;
    if (keys['ArrowDown']) player.y += player.speed;
    if (keys['ArrowLeft']) player.x -= player.speed;
    if (keys['ArrowRight']) player.x += player.speed;

    // Limitar movimiento dentro del canvas
    player.x = Math.max(0, Math.min(canvas.width - player.size, player.x));
    player.y = Math.max(0, Math.min(canvas.height - player.size, player.y));
}

// Generar enemigos
function spawnEnemies() {
    if (enemies.length < 15) {
        const enemyType = enemyTypes[Math.floor(Math.random() * enemyTypes.length)];
        const enemy = {
            x: Math.random() * canvas.width,
            y: Math.random() * canvas.height,
            size: 20,
            speed: 2,
            type: enemyType
        };
        enemies.push(enemy);
    }
}

// Movimiento de los enemigos hacia el jugador
function moveEnemies() {
    enemies.forEach(enemy => {
        const dx = player.x - enemy.x;
        const dy = player.y - enemy.y;
        const distance = Math.sqrt(dx * dx + dy * dy);
        enemy.x += (dx / distance) * enemy.speed;
        enemy.y += (dy / distance) * enemy.speed;
    });
}

// Dibujar el jugador
function drawPlayer() {
    ctx.fillStyle = 'blue';
    ctx.fillRect(player.x, player.y, player.size, player.size);
}

// Dibujar enemigos
function drawEnemies() {
    enemies.forEach(enemy => {
        ctx.fillStyle = 'red';
        ctx.fillRect(enemy.x, enemy.y, enemy.size, enemy.size);
    });
}

// Sistema de experiencia y nivel
function checkLevelUp() {
    if (player.xp >= player.xpToLevelUp) {
        player.level++;
        player.xp = 0;
        player.xpToLevelUp *= 1.5;
        showWeaponChoices();
    }
}

// Control de teclas
const keys = {};
window.addEventListener('keydown', e => {
    keys[e.key] = true;
});

window.addEventListener('keyup', e => {
    keys[e.key] = false;
});

// Bucle principal del juego
function gameLoop() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    movePlayer();
    drawPlayer();

    spawnEnemies();
    moveEnemies();
    drawEnemies();

    checkLevelUp();

    requestAnimationFrame(gameLoop);
}

// Iniciar el bucle del juego
gameLoop();
