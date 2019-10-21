package com.sregnard.themebreaker.classes.engine;

import java.util.ArrayList;

import com.sregnard.themebreaker.activities.GameEngine;
import com.sregnard.themebreaker.activities.ThemeBreaker;
import com.sregnard.themebreaker.classes.brickbreaker.Ball;
import com.sregnard.themebreaker.classes.brickbreaker.BounceType;
import com.sregnard.themebreaker.classes.brickbreaker.Brick;
import com.sregnard.themebreaker.classes.brickbreaker.Paddle;
import com.sregnard.themebreaker.classes.engine.state.StateGame;
import com.sregnard.themebreaker.classes.game.Difficulty;
import com.sregnard.themebreaker.classes.game.DifficultyManager;
import com.sregnard.themebreaker.classes.game.Level;
import com.sregnard.themebreaker.classes.game.LevelManager;
import com.sregnard.themebreaker.classes.game.Map;
import com.sregnard.themebreaker.classes.game.Sprite;
import com.sregnard.themebreaker.classes.geometry.Geometry;
import com.sregnard.themebreaker.classes.geometry.Line;
import com.sregnard.themebreaker.classes.geometry.Point;
import com.sregnard.themebreaker.classes.geometry.Rectangle;
import com.sregnard.themebreaker.classes.geometry.ShapeInterface;
import com.sregnard.themebreaker.classes.others.Observable;
import com.sregnard.themebreaker.classes.others.Observer;
import com.sregnard.themebreaker.classes.sound.Music;
import com.sregnard.themebreaker.classes.sound.SFX;

public class ModelTB implements Observable, Observer {

	// Modification du score selon les actions
	public static int SCORE_MODIFIER_TOUCHING_PADDLE = -1;
	public static int SCORE_MODIFIER_TOUCHING_BRICK = 10;
	public static int SCORE_MODIFIER_LOSING_LIFE = -100;

	// Bonus - Malus fin de partie
	public static int SCORE_MODIFIER_WIN_GAME = 1000;
	public static int SCORE_MODIFIER_LOSE_GAME = -500;

	// Modification du score selon le temps écoulé pour terminer la partie
	public static int SCORE_MODIFIER_BELOW_TIME = 5;
	public static int SCORE_MODIFIER_ABOVE_TIME = -1;

	// Temps estimé pour détruire une brique
	public static int RATIO_SECOND_PER_RESISTANCE = 5;

	// Nombre d'images par seconde qu'on tentera d'afficher
	public static int TARGET_FPS = 60;

	Music backgroundMusic = ThemeBreaker.backgroundMusic;

	ArrayList<Observer> obs = new ArrayList<Observer>();
	boolean needUpdate = true;

	GameEngine owner;
	StateGame game;

	public Level level;
	public Difficulty difficulty;
	public Map map;
	public ArrayList<Brick> bricks;

	public int life;
	public int score;

	public Paddle paddle;
	public Ball ball;
	float heightPaddle, widthPaddle, radiusBall;

	public boolean updateBricks = false;
	public boolean updateLife = true;
	public boolean updateScore = false;

	public boolean gameWon;

	BounceType bounceType;
	ArrayList<Brick> bounceTarget = new ArrayList<Brick>();
	boolean twoTargets = false;

	long estimatedFinishTime; // temps estimé pour terminer la partie en
								// secondes
	long timeRemaining; // temps restant avant de perdre le bonus au temps en ms
	long frequency; // temps entre chaque éxecution en ms
	Thread thread;
	Runnable task = new Runnable() {
		@Override
		public void run() {
			task();
		}
	};

	public boolean initialized = false;

	public ModelTB(GameEngine owner) {
		this.owner = owner;

		backgroundMusic.setMedia(Music.MUSIC_GAME);
		backgroundMusic.pause();

		game = owner.getGameState();
		game.addObs(this);

		level = LevelManager.getLevel(ThemeBreaker.LEVEL);
		level.generateMap();
		difficulty = DifficultyManager.getDifficulty(ThemeBreaker.DIFFICULTY);
		map = level.getMap();
		bricks = map.bricks();
		life = DifficultyManager.maximumDifficulty().getID()
				- ThemeBreaker.DIFFICULTY;
		score = 0;

		heightPaddle = 0.5f;
		widthPaddle = heightPaddle * 8;
		radiusBall = heightPaddle * 0.75f;
		paddle = new Paddle(new Point(), heightPaddle, widthPaddle);
		ball = new Ball(new Point(), radiusBall);
		ball.setSpeed(Ball.DEFAULT_SPEED * difficulty.getID());
		centerPaddle();
		alignBall();
	}

	public void centerPaddle() {
		paddle.setCenter(new Point(map.width() / 2, map.height()
				- paddle.height() / 2 - 1));
	}

	public void alignBall() {
		ball.setCenter(new Point(paddle.center().getX(), paddle.top()
				- ball.radius()));
	}

	public void init() {
		System.out.println("ModelBB : init()");
		initialized = true;
		game.initialized();
		estimatedFinishTime = 0;
		for (Brick b : bricks)
			estimatedFinishTime += b.resistance * RATIO_SECOND_PER_RESISTANCE;
		timeRemaining = estimatedFinishTime * 1000;
		frequency = 1000 / TARGET_FPS;

		// Thread
		thread = new Thread(task);
		thread.start();
	}

	public void reset() {
		game.pause();
		centerPaddle();
		alignBall();
		ball.setDirection(Ball.D_UP);
		paddle.update();
		needUpdate = true;
		updateAll();
		game.initialized();
	}

	public void task() {
		try {
			// Tant que la partie existe et n'est pas terminée
			while (!game.isOff() && !game.isOver()) {
				// Si le jeu est en pause, attendre 100ms
				if (game.isPaused())
					if (backgroundMusic.isPlaying())
						backgroundMusic.pause();
				Thread.sleep(100);
				// Tant que le jeu est en cours
				while (game.isRunning()) {
					if (backgroundMusic.isPaused())
						backgroundMusic.play();
					moveBall();
					updateAll();
					Thread.sleep(frequency);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void movePaddle(int direction, float speed) {
		// Pas de mouvement à effectuer
		if (direction == 0 || speed == 0 || game.isOff() || game.isOver()
				|| game.isPaused())
			return;

		// Translation sur les x
		float dx = direction * speed;

		// Calcul la prochaine position de la raquette
		float nextPos = paddle.nextPos(dx);

		// Copie de la raquette
		Paddle nextPaddle = new Paddle(paddle);

		// Placement de la copie à la prochaine destination prévue
		nextPaddle.move(dx);

		// Vérification des collisions
		if (!((bounceType = collision(nextPaddle)) == BounceType.NONE)) {
			// La raquette rentre en collision avec le mur
			if (bounceType.is(BounceType.WALL)) {
				// Mur de gauche, on y colle la raquette
				if (nextPos < 0 && direction == -1)
					paddle.getPos().setX(0);
				// Mur de droite, on y colle la raquette
				else if (nextPos + paddle.width() > map.width()
						&& direction == 1)
					paddle.getPos().setX(map.width() - paddle.width());
			}
			// La raquette rentre en collision avec la balle
			else if (bounceType.is(BounceType.BALL_PADDLE))
				// On vérifie que la raquette n'est pas déjà en collision avec
				// la balle
				if (collision(paddle) == BounceType.NONE
				// Ou bien si le jeu est dans l'état initialisé, la balle étant
				// collée à la raquette avant d'être lancée
						|| game.isInitialized()) {
					ball.move(dx, 0);
					paddle.move(dx);
				}
		}
		// Sinon, on déplace la raquette de dx
		else {
			paddle.move(dx);
		}

		// Indique que la raquette a besoin d'être mise à jour par la vue
		paddle.update();

		// Si le jeu est dans l'état l'initialisé
		if (game.isInitialized()) {
			// On aligne la balle avec la raquette
			alignBall();
			updateAll();
		}
	}

	public void moveBall() {
		// Calcule la prochaine position de la balle
		Point newPos = ball.nextPos();

		// Copie de la balle
		Ball nextBall = new Ball(ball);

		// Placement de la copie à la prochaine destination prévue
		nextBall.setPos(newPos);

		// Vérification des collisions
		if (!((bounceType = collision(nextBall)) == BounceType.NONE)) {
			bounce();
			newPos = ball.nextPos();
		}

		// Si le jeu est en cours on déplace la balle
		if (game.isRunning())
			ball.setPos(newPos);

		// Si la balle a touché une brique et que celle-ci a été marquée pour
		// destruction
		if (!bounceTarget.isEmpty())
			destroyBrick();
	}

	public BounceType collision(ShapeInterface s) {

		// Vérification des collisions
		if (collisionGround(s)) {
			return BounceType.GROUND;
		}
		if (collisionRoof(s)) {
			return BounceType.ROOF;
		}
		if (collisionWall(s)) {
			return BounceType.WALL;
		}
		if (collisionBallPaddle(s)) {
			return BounceType.BALL_PADDLE;
		}
		if (collisionBricks(s))
			return BounceType.BRICK;

		// Aucune collision trouvée
		return BounceType.NONE;
	}

	// Collision avec le "sol"
	public boolean collisionGround(ShapeInterface s) {
		// Si le centre de la balle est-en dessous du dessus de la raquette elle
		// est irrécupérable
		if (s instanceof Ball)
			return (s.center().getY() + ((Ball) s).radius() >= map.height());
		return false;
	}

	// Collision avec le "toit"
	public boolean collisionRoof(ShapeInterface s) {
		if (s instanceof Ball)
			return (s.center().getY() - ((Ball) s).radius() <= 0);
		return false;
	}

	// Collision avec les "murs"
	public boolean collisionWall(ShapeInterface s) {
		if (s instanceof Ball)
			return (s.center().getX() - ((Ball) s).radius() <= 0 || s.center()
					.getX() + ((Ball) s).radius() >= map.width());
		if (s instanceof Paddle)
			return (s.center().getX() - ((Paddle) s).width() / 2 <= 0 || s
					.center().getX() + ((Paddle) s).width() / 2 >= map.width());
		return false;
	}

	// Collision avec la raquette
	public boolean collisionBallPaddle(ShapeInterface s) {
		if (s instanceof Ball)
			return Geometry.circleInterRectangle((Ball) s, paddle);
		if (s instanceof Paddle)
			return Geometry.circleInterRectangle(ball, (Paddle) s);
		return false;
	}

	// Collision avec une des briques
	public boolean collisionBricks(ShapeInterface s) {
		boolean collision = false;
		twoTargets = false;
		for (Brick brick : bricks)
			if (collisionBrick(s, brick)) {
				if (!collision) {
					collision = true;
				} else {
					twoTargets = true;
					return true;
				}
			}
		return collision;
	}

	// Collision avec une brique
	public boolean collisionBrick(ShapeInterface s, Brick brick) {
		if (s instanceof Ball)
			if (Geometry.circleInterRectangle((Ball) s, brick)) {
				bounceTarget.add(brick);
				return true;
			}
		return false;
	}

	// Retourne le nouvel angle de direction de la balle
	public static float ballBounce(Ball ball, Rectangle r) {
		// if (r instanceof Paddle)
		// return ((Paddle) r).ballBounce(ball);
		Point center = ball.center();
		float direction = ball.direction();
		Line tangent = Geometry.tangentImpactRectangle(center, r);
		float tangentAngle = tangent.getAngle();
		return 2 * tangentAngle - direction;
	}

	// Redirection de la balle au rebond
	public void bounce() {
		if (bounceType.is(BounceType.GROUND)) {
			ball.setDirection(-ball.direction());
			loseLife();
		} else if (bounceType.is(BounceType.BALL_PADDLE)) {
			modScore(SCORE_MODIFIER_TOUCHING_PADDLE);
			ball.setDirection(ballBounce(ball, paddle));
		} else if (bounceType.is(BounceType.BRICK)) {
			// Si la balle touche deux briques à la fois
			if (twoTargets) {
				Rectangle tmp = Rectangle.merge(bounceTarget.get(0).getRekt(),
						bounceTarget.get(1).getRekt());
				ball.setDirection(ballBounce(ball, tmp));
			}
			// Une seule brique
			else {
				ball.setDirection(ballBounce(ball, bounceTarget.get(0)));
			}

		} else if (bounceType.is(BounceType.ROOF)) {
			ball.setDirection(-ball.direction());
		} else if (bounceType.is(BounceType.WALL)) {
			ball.setDirection(180 - ball.direction());
		}
	}

	// Destruction de la brique ciblée
	public void destroyBrick() {
		for (Brick b : bounceTarget) {
			modScore(SCORE_MODIFIER_TOUCHING_BRICK);
			b.resistance--;
			b.update();
			if (b.resistance == 0) {
				SFX.playBreak();
				level.getMap().bricks().remove(b);
			} else {
				SFX.playCollision();
			}
		}
		bounceTarget.clear();
		updateBricks = true;
		if (noBricks())
			game.over();
	}

	// Retourne vrai s'il reste des briques, faux sinon
	public boolean noBricks() {
		return (level.getMap().bricks().isEmpty());
	}

	// Perte d'une vie
	public void loseLife() {
		modScore(SCORE_MODIFIER_LOSING_LIFE);
		if (noLife())
			game.over();
		else {
			life--;
			SFX.playFail();
			reset();
		}
		updateLife = true;
	}

	// Retourne vrai s'il ne reste plus de vie, faux sinon
	public boolean noLife() {
		return (life < 1);
	}

	public void modScore(int x) {
		// Si on modifie le score positivement
		if (x > 0)
			x *= difficulty.getID();
		score += x;
		updateScore = true;
	}

	public void scoreTemps() {
		// Si la partie a été terminée "en avance"
		if (timeRemaining > 0)
			modScore((int) timeRemaining / 1000 * SCORE_MODIFIER_BELOW_TIME);
		else
			modScore((int) timeRemaining / 1000 * (-SCORE_MODIFIER_ABOVE_TIME));
	}

	// Appelé lorsque le jeu est fini
	public void finishGame() {
		if (noBricks())
			gameWon();
		else if (noLife())
			gameLost();
		needUpdate = true;
	}

	public void gameLost() {
		backgroundMusic.setMedia(Music.MUSIC_LOSE);
		backgroundMusic.setLooping(false);
		modScore(SCORE_MODIFIER_LOSE_GAME);
		gameWon = false;
		System.out.println("Partie perdue !");
	}

	public void gameWon() {
		backgroundMusic.setMedia(Music.MUSIC_WIN);
		backgroundMusic.setLooping(false);
		scoreTemps();
		modScore(SCORE_MODIFIER_WIN_GAME);
		gameWon = true;
		System.out.println("Partie gagnée !");
		unlock();
	}

	private void unlock() {
		int nextLevel = level.number() + 1;
		if (nextLevel > ThemeBreaker.LEVEL_PROGRESSION)
			nextLevel--;
		ThemeBreaker.setLevel(nextLevel);
	}

	public void closeActivity() {
		game.off();
		backgroundMusic.setMedia(Music.MUSIC_MENU);
		Sprite.release();
		owner.finish();
	}

	/***** Observable *****/

	@Override
	public void addObs(Observer obs) {
		this.obs.add(obs);
	}

	@Override
	public void delObs(Observer obs) {
		this.obs.remove(obs);
	}

	@Override
	public void delAllObs() {
		this.obs.clear();
	}

	@Override
	public void updateAll() {
		needUpdate = true;
		for (Observer obs : this.obs)
			obs.update();
	}

	public boolean needUpdate() {
		return needUpdate;
	}

	@Override
	public void updateFinished() {
		paddle.updated();
		updateBricks = false;
		updateLife = false;
		updateScore = false;
		needUpdate = false;
	}

	/***** Observateur *****/

	@Override
	public void update() {
		if (game.isInitialized())
			System.out.println("En attente de lancement de la part du joueur.");
		else if (game.isRunning())
			System.out.println("Partie en cours.");
		else if (game.isPaused())
			System.out.println("Jeu mis en pause.");
		else if (game.isOver())
			finishGame();
		else if (game.isOff())
			System.out.println("La partie n'existe plus");
	}
}
