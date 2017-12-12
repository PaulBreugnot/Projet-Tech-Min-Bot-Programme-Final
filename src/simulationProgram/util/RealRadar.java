package simulationProgram.util;

import java.util.ArrayList;

import commonInterface.Captor;
import simulationProgram.simMap.Map;
import simulationProgram.simMap.Obstacle;
import simulationProgram.simRobot.SimRobot;
import simulationProgram.simRobot.SimCaptor;

public class RealRadar {
	private double mapWidth;
	private double mapHeight;
	private double xRadar;
	private double yRadar;
	private double robotSize;
	private double alphaOrientation;
	private ArrayList<Captor> robotCaptors;
	private double edgeDistance;

	private ArrayList<Obstacle> obstaclesList;
	private SimRobot titi;
	private Map map;

	public boolean crash = false;
	// il y a l'arraylist capteur, qu'il faut recuperer du robot

	public RealRadar(ArrayList<Obstacle> obstaclesList, SimRobot titi, Map map) {
		this.obstaclesList = obstaclesList;
		this.titi = titi;
		this.map = map;
	}

	public void updateCaptorDistances() {
		mapWidth = map.getWidth();
		mapHeight = map.getHeight();
		xRadar = titi.getXRadar();
		yRadar = titi.getYRadar();

		robotSize = titi.getRobotSize();
		alphaOrientation = titi.getAlphaOrientation();
		robotCaptors = titi.getRobotCaptors();

		if (xRadar > mapWidth - robotSize || xRadar < robotSize || yRadar > mapHeight - robotSize
				|| yRadar < robotSize) {
			// capteurs.set(0, (double) -1);
			for (Captor captor : robotCaptors)
				captor.setDistance(0);

			return;
		}

		for (int j = 0; j < 5; j++) {
			double absoluteCaptorOrientation = alphaOrientation
					+ ((SimCaptor) titi.getRobotCaptors().get(j)).getCaptorOrientation();
			
			if(absoluteCaptorOrientation >= 360) {
				absoluteCaptorOrientation = absoluteCaptorOrientation - 360;
			}
			if(absoluteCaptorOrientation < 0) {
				absoluteCaptorOrientation = absoluteCaptorOrientation + 360;
			}
			
			edgeDistance = getEdgeDistance(absoluteCaptorOrientation);
			if (obstaclesList.size() < 1) {
				robotCaptors.get(j).setDistance(edgeDistance - titi.getRobotSize() + titi.getRadarOffset());
			} else {
				ArrayList<Double> measuredDistance = new ArrayList<>();
				for (Obstacle obstacle : obstaclesList) {
					if ((obstacle.getXPos() - xRadar) * (obstacle.getXPos() - xRadar) + (obstacle.getYPos() - yRadar)
							* (obstacle.getYPos() - yRadar) < (obstacle.getRadius() + robotSize)
									* (obstacle.getRadius() + robotSize)) {
						// le robot n'est pas dans l'obstacle
						// détermination de la distance des capteurs à l'obstacle
						return;
					}

					ArrayList<Double> solutions = new ArrayList<Double>();
					solutions = getIntersectionPoints(absoluteCaptorOrientation, obstacle.getXPos(), obstacle.getYPos(),
							obstacle.getRadius());

					if (solutions.size() == 0) {
						// pas de solutions avec les obstacles, regarder le bord de terrain
						edgeDistance = getEdgeDistance(absoluteCaptorOrientation);
						measuredDistance.add(edgeDistance);

					} else if (solutions.size() == 2) {
						// 1seule solution : vérifier juste la demi-droite
						if (0 <= absoluteCaptorOrientation && absoluteCaptorOrientation < 180) {
							if (solutions.get(1) >= yRadar) {
								measuredDistance
										.add(Math.min(edgeDistance, Math.sqrt(Math.pow(xRadar - solutions.get(0), 2)
												+ Math.pow(yRadar - solutions.get(1), 2))));
							}
						}

						else {
							if (solutions.get(1) <= yRadar) {
								measuredDistance
										.add(Math.min(edgeDistance, Math.sqrt(Math.pow(xRadar - solutions.get(0), 2)
												+ Math.pow(yRadar - solutions.get(1), 2))));
							}
						}
					} else {
						// 2 solutions
						if (0 <= absoluteCaptorOrientation && absoluteCaptorOrientation < 180) {
							if (solutions.get(1) >= yRadar && solutions.get(3) >= yRadar) {
								measuredDistance.add(Math.min(edgeDistance,
										Math.min(
												Math.sqrt(Math.pow(xRadar - solutions.get(0), 2)
														+ Math.pow(yRadar - solutions.get(1), 2)),
												Math.sqrt(Math.pow(xRadar - solutions.get(2), 2)
														+ Math.pow(yRadar - solutions.get(3), 2)))));
							} else if (solutions.get(1) >= yRadar && solutions.get(3) <= yRadar) {
								measuredDistance
										.add(Math.min(edgeDistance, Math.sqrt(Math.pow(xRadar - solutions.get(0), 2)
												+ Math.pow(yRadar - solutions.get(1), 2))));
							} else if (solutions.get(1) <= yRadar && solutions.get(3) >= yRadar) {
								measuredDistance
										.add(Math.min(edgeDistance, Math.sqrt(Math.pow(xRadar - solutions.get(2), 2)
												+ Math.pow(yRadar - solutions.get(3), 2))));
							} else if (solutions.get(1) <= yRadar && solutions.get(3) <= yRadar) {
								measuredDistance.add(edgeDistance);
							}
						} else {
							if (solutions.get(1) >= yRadar && solutions.get(3) <= yRadar) {
								measuredDistance
										.add(Math.min(edgeDistance, Math.sqrt(Math.pow(xRadar - solutions.get(2), 2)
												+ Math.pow(yRadar - solutions.get(3), 2))));
							} else if (solutions.get(1) <= yRadar && solutions.get(3) >= yRadar) {
								measuredDistance
										.add(Math.min(edgeDistance, Math.sqrt(Math.pow(xRadar - solutions.get(0), 2)
												+ Math.pow(yRadar - solutions.get(1), 2))));
							} else if (solutions.get(1) <= yRadar && solutions.get(3) <= yRadar) {
								measuredDistance.add(Math.min(edgeDistance,
										Math.min(
												Math.sqrt(Math.pow(xRadar - solutions.get(0), 2)
														+ Math.pow(yRadar - solutions.get(1), 2)),
												Math.sqrt(Math.pow(xRadar - solutions.get(2), 2)
														+ Math.pow(yRadar - solutions.get(3), 2)))));
							} else if (solutions.get(1) >= yRadar && solutions.get(3) >= yRadar) {
								measuredDistance.add(edgeDistance);
							}
						}
					}
				}
				robotCaptors.get(j).setDistance(MinArrayList.minArrayList(measuredDistance) - titi.getRobotSize() + titi.getRadarOffset());
			}
		}
	}

	public double getEdgeDistance(double alpharadar) {

		if (alpharadar < 0) {
			alpharadar = alpharadar + 360;
		}
		if (alpharadar >= 360) {
			alpharadar = alpharadar - 360;
		}

		double a;
		double b;

		// equation de la droite de vision du capteur
		a = Math.tan(alpharadar * Math.PI / 180);
		b = (yRadar - a * xRadar);

		double xintersection1;
		double xintersection2;
		double yintersection3;
		double yintersection4;

		double d1;
		double d2;
		double d3;
		double d4;

		xintersection1 = (mapHeight - b) / a;// haut
		xintersection2 = -b / a;// axe des abscisses
		yintersection3 = a * mapWidth + b;// bord droit
		yintersection4 = b;// bord gauche, axe des ordonnées

		// d1 : distance to top edge if defined (/!\ bottom edge in graphic window)
		// d2 : distance to bottom edge if defined (/!\ top edge in graphic window)
		// d3 : distance to right edge if defined
		// d4 : distance to left edge if defined
		if (0 <= xintersection1 && xintersection1 <= mapWidth) {
			d1 = Math.sqrt(Math.pow(mapHeight - yRadar, 2) + Math.pow(xRadar - xintersection1, 2));
		} else
			d1 = -1;
		if (0 <= xintersection2 && xintersection2 <= mapWidth) {
			d2 = Math.sqrt(Math.pow(yRadar, 2) + Math.pow(xRadar - xintersection2, 2));
		} else
			d2 = -1;
		if (0 <= yintersection3 && yintersection3 <= mapHeight) {
			d3 = Math.sqrt(Math.pow(yintersection3 - yRadar, 2) + Math.pow(mapWidth - xRadar, 2));
		} else
			d3 = -1;
		if (0 <= yintersection4 && yintersection4 <= mapHeight) {
			d4 = Math.sqrt(Math.pow(yintersection4 - yRadar, 2) + Math.pow(xRadar, 2));
		} else
			d4 = -1;

		// The "or" conditions corresponds to cases where the captor points exactly an
		// angle, what produces some bugs.
		if ((0 <= alpharadar && alpharadar < 90 && d1 == -1) | ((d1 != -1) && (d1 == d3))) {
			return d3;
		} else if (0 <= alpharadar && alpharadar < 90 && d3 == -1) {
			return d1;
		} else if (90 <= alpharadar && alpharadar < 180 && d1 == -1 | ((d1 != -1) && (d1 == d4))) {
			return d4;
		} else if (90 <= alpharadar && alpharadar < 180 && d4 == -1) {
			return d1;
		} else if (180 <= alpharadar && alpharadar < 270 && d2 == -1 | ((d2 != -1) && (d2 == d4))) {
			return d4;
		} else if (180 <= alpharadar && alpharadar < 270 && d4 == -1) {
			return d2;
		} else if (270 <= alpharadar && alpharadar < 360 && d2 == -1 | ((d2 != -1) && (d2 == d3))) {
			return d3;
		} else if (270 <= alpharadar && alpharadar < 360 && d3 == -1) {
			return d2;
		}
		System.out.println("Alpharadar = " + alpharadar);
		System.out.println("coucou!");
		return 0.2;

	}

	public ArrayList<Double> getIntersectionPoints(double alpharadar, double cx, double cy, double r) {
		ArrayList<Double> lst = new ArrayList<Double>();

		if (alpharadar < 0) {
			alpharadar = alpharadar + 360;
		}
		if (alpharadar > 360) {
			alpharadar = alpharadar - 360;
		}

		double a;
		double b;

		// equation de la droite de vision du capteur
		if (alpharadar != 270 && alpharadar != 90) {
		a = Math.tan(alpharadar * Math.PI / 180);
		b = (yRadar - a * xRadar);

		double A = 1 + a * a;
		double B = 2 * (-cx + a * b - a * cy);
		double C = cx * cx + cy * cy + b * b - 2 * b * cy - r * r;
		double delta = B * B - 4 * A * C;

		if (delta > 0) {
			double x = (-B - Math.sqrt(delta)) / (2 * A);
			double y = a * x + b;
			lst.add(x);
			lst.add(y);

			x = (-B + Math.sqrt(delta)) / (2 * A);
			y = a * x + b;
			lst.add(x);
			lst.add(y);

		} else if (delta == 0) {
			double x = -B / (2 * A);
			double y = a * x + b;

			lst.add(x);
			lst.add(y);
		}
		}
		else {
			if(cx-r < xRadar && xRadar < cx+r) {
				lst.add(xRadar);
				lst.add(cy + Math.sqrt(r*r - Math.pow(cx - xRadar,2)));
				lst.add(xRadar);
				lst.add(cy - Math.sqrt(r*r - Math.pow(cx - xRadar,2)));
			}
			else if (cx-r == xRadar | xRadar == cx+r) {
				lst.add(xRadar);
				lst.add(cy);
			}
		}
		return lst;
	}

}
