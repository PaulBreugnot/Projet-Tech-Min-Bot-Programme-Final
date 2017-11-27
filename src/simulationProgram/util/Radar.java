package simulationProgram.util;

import java.util.ArrayList;

import commonInterface.Captor;
import simulationProgram.simMap.Map;
import simulationProgram.simMap.Obstacle;
import simulationProgram.simRobot.SimRobot;

public class Radar {
	private double mapWidth;
	private double mapHeight;
	private double xRobot;
	private double yRobot;
	private double robotSize;
	private double alphaOrientation;
	private ArrayList<Captor> robotCaptors;
	private double edgeDistance;

	private ArrayList<Obstacle> obstaclesList;
	private SimRobot titi;
	private Map map;

	public boolean crash = false;
	// il y a l'arraylist capteur, qu'il faut recuperer du robot

	public Radar(ArrayList<Obstacle> obstaclesList, SimRobot titi, Map map) {
		this.obstaclesList = obstaclesList;
		this.titi = titi;
		this.map = map;
	}

	public void updateCaptorDistances() {
		mapWidth = map.getWidth();
		mapHeight = map.getHeight();
		xRobot = titi.getXPos();
		yRobot = titi.getYPos();

		robotSize = titi.getRobotSize();
		alphaOrientation = titi.getAlphaOrientation();
		robotCaptors = titi.getRobotCaptors();

		if (xRobot > mapWidth - robotSize || xRobot < robotSize || yRobot > mapHeight - robotSize
				|| yRobot < robotSize) {
			// capteurs.set(0, (double) -1);
			for (Captor captor : robotCaptors)
				captor.setDistance(0);

			return;
		}

		for (int j = 0; j < 5; j++) {
			if (obstaclesList.size() < 1) {
				edgeDistance = getEdgeDistance(alphaOrientation - 80 + 40 * j, xRobot, yRobot);
				robotCaptors.get(j).setDistance(edgeDistance);
			} else {
				ArrayList<Double> measuredDistance = new ArrayList<>();
				for (Obstacle obstacle : obstaclesList) {
					if ((obstacle.getXPos() - xRobot) * (obstacle.getXPos() - xRobot) + (obstacle.getYPos() - yRobot)
							* (obstacle.getYPos() - yRobot) < (obstacle.getRadius() + robotSize)
									* (obstacle.getRadius() + robotSize)) {
						// le robot n'est pas dans l'obstacle
						// d�termination de la distance des capteurs � l'obstacle
						System.out.println("lol");
						return;
					}

					ArrayList<Double> solutions = new ArrayList<Double>();
					System.out.println("nb solutions = " + solutions.size());
					solutions = getIntersectionPoints(alphaOrientation - 80 + 40 * j, obstacle.getXPos(), obstacle.getYPos(),
							obstacle.getRadius());
					
					// alphaOrientation - 80 + 40 * j est l'angle du capteur J
					
					if (solutions.size() == 0) {
						// pas de solutions avec les obstacles, regarder le bord de terrain
						edgeDistance = getEdgeDistance(alphaOrientation - 80 + 40 * j, xRobot, yRobot);
						robotCaptors.get(j).setDistance(edgeDistance);
						// return null; //Il faut assigner la distance au 5 capteurs non?
					} else if (solutions.size() == 2) {
						// 1seule solution : v�rifier juste la demi-droite
						if (0 < alphaOrientation && alphaOrientation < Math.PI) {
							if (solutions.get(1) > yRobot) {
								/*
								 * robotCaptors.get(j).setDistance( Math.min(edgeDistance, ((xRobot -
								 * solutions.get(0)) * (xRobot - solutions.get(0)) + (yRobot - solutions.get(1))
								 * * (yRobot - solutions.get(1)))));
								 */
								measuredDistance.add(Math.min(edgeDistance,
										((xRobot - solutions.get(0)) * (xRobot - solutions.get(0))
												+ (yRobot - solutions.get(1)) * (yRobot - solutions.get(1)))));
							}
						}

						else {
							if (solutions.get(1) < yRobot) {
								/*
								 * robotCaptors.get(j).setDistance( Math.min(edgeDistance, ((xRobot -
								 * solutions.get(0)) * (xRobot - solutions.get(0)) + (yRobot - solutions.get(1))
								 * * (yRobot - solutions.get(1)))));
								 */
								measuredDistance.add(Math.min(edgeDistance,
										((xRobot - solutions.get(0)) * (xRobot - solutions.get(0))
												+ (yRobot - solutions.get(1)) * (yRobot - solutions.get(1)))));
							}

							else {
								// 2 solutions
								if (0 < alphaOrientation && alphaOrientation < Math.PI) {
									if (solutions.get(1) > yRobot) {
										solutions.remove(3);
										solutions.remove(2);

									}
									if (0 < alphaOrientation && alphaOrientation < Math.PI) {
										if (solutions.get(1) > yRobot && solutions.get(3) > yRobot) {
											/*
											 * robotCaptors.get(j).setDistance(Math.min(edgeDistance, Math.min( ((xRobot
											 * - solutions.get(0)) * (xRobot - solutions.get(0)) + (yRobot -
											 * solutions.get(1)) * (yRobot - solutions.get(1))), ((xRobot -
											 * solutions.get(2)) * (xRobot - solutions.get(2)) + (yRobot -
											 * solutions.get(3)) * (yRobot - solutions.get(3))))));
											 */
											measuredDistance
													.add(Math
															.min(edgeDistance, Math.min(
																	((xRobot - solutions.get(0))
																			* (xRobot - solutions.get(0))
																			+ (yRobot
																					- solutions.get(1))
																					* (yRobot - solutions.get(1))),
																	((xRobot - solutions.get(2))
																			* (xRobot - solutions.get(2))
																			+ (yRobot - solutions.get(3))
																					* (yRobot - solutions.get(3))))));
										} else if (solutions.get(1) > yRobot && solutions.get(3) < yRobot) {
											/*
											 * robotCaptors.get(j).setDistance(Math.min(edgeDistance, ((xRobot -
											 * solutions.get(0)) * (xRobot - solutions.get(0)) + (yRobot -
											 * solutions.get(1)) * (yRobot - solutions.get(1)))));
											 */
											measuredDistance.add(Math.min(edgeDistance,
													((xRobot - solutions.get(0)) * (xRobot - solutions.get(0))
															+ (yRobot - solutions.get(1))
																	* (yRobot - solutions.get(1)))));
										} else if (solutions.get(1) < yRobot && solutions.get(3) > yRobot) {
											/*
											 * robotCaptors.get(j).setDistance(Math.min(edgeDistance, ((xRobot -
											 * solutions.get(2)) * (xRobot - solutions.get(3)) + (yRobot -
											 * solutions.get(2)) * (yRobot - solutions.get(3)))));
											 */
											measuredDistance.add(Math.min(edgeDistance,
													((xRobot - solutions.get(2)) * (xRobot - solutions.get(3))
															+ (yRobot - solutions.get(2))
																	* (yRobot - solutions.get(3)))));
										} else if (solutions.get(1) < yRobot && solutions.get(3) < yRobot) {
										}
									} else {
										if (solutions.get(1) > yRobot && solutions.get(3) > yRobot) {

										} else if (solutions.get(1) > yRobot && solutions.get(3) < yRobot) {
											/*
											 * robotCaptors.get(j).setDistance(Math.min(edgeDistance, ((xRobot -
											 * solutions.get(2)) * (xRobot - solutions.get(3)) + (yRobot -
											 * solutions.get(2)) * (yRobot - solutions.get(3)))));
											 */
											measuredDistance.add(Math.min(edgeDistance,
													((xRobot - solutions.get(2)) * (xRobot - solutions.get(3))
															+ (yRobot - solutions.get(2))
																	* (yRobot - solutions.get(3)))));
										} else if (solutions.get(1) < yRobot && solutions.get(3) > yRobot) {
											/*
											 * robotCaptors.get(j).setDistance(Math.min(edgeDistance, ((xRobot -
											 * solutions.get(0)) * (xRobot - solutions.get(0)) + (yRobot -
											 * solutions.get(1)) * (yRobot - solutions.get(1)))));
											 */
											measuredDistance.add(Math.min(edgeDistance,
													((xRobot - solutions.get(0)) * (xRobot - solutions.get(0))
															+ (yRobot - solutions.get(1))
																	* (yRobot - solutions.get(1)))));
										} else if (solutions.get(1) < yRobot && solutions.get(3) < yRobot) {
											/*
											 * robotCaptors.get(j).setDistance(Math.min(edgeDistance, Math.min( ((xRobot
											 * - solutions.get(0)) * (xRobot - solutions.get(0)) + (yRobot -
											 * solutions.get(1)) * (yRobot - solutions.get(1))), ((xRobot -
											 * solutions.get(2)) * (xRobot - solutions.get(2)) + (yRobot -
											 * solutions.get(3)) * (yRobot - solutions.get(3))))));
											 */
											measuredDistance
													.add(Math
															.min(edgeDistance, Math.min(
																	((xRobot - solutions.get(0))
																			* (xRobot - solutions.get(0))
																			+ (yRobot
																					- solutions.get(1))
																					* (yRobot - solutions.get(1))),
																	((xRobot - solutions.get(2))
																			* (xRobot - solutions.get(2))
																			+ (yRobot - solutions.get(3))
																					* (yRobot - solutions.get(3))))));
										}

										else {
											if (solutions.get(1) < yRobot) {
												/*
												 * robotCaptors.get(j) .setDistance(Math.min(edgeDistance, ((xRobot -
												 * solutions.get(0)) * (xRobot - solutions.get(0)) + (yRobot -
												 * solutions.get(1)) (yRobot - solutions.get(1)))));
												 */
												measuredDistance.add(Math.min(edgeDistance,
														((xRobot - solutions.get(0)) * (xRobot - solutions.get(0))
																+ (yRobot - solutions.get(1))
																		* (yRobot - solutions.get(1)))));
											} else {
											}
										}
									}

								}

								else {
									// le robot est dans l'obstacle
									// capteurs.set(0, (double) -1);
									robotCaptors.get(j).setDistance(0);
								}
							}
						}
					}

				}
				robotCaptors.get(j).setDistance(MinArrayList.minArrayList(measuredDistance));
			}
			System.out.println("Captor " + j + ": " + robotCaptors.get(j).getDistance());
		}
	}

	public double getEdgeDistance(double alpharadar, double xrobot, double yrobot) {

		if (alpharadar < 0) {
			alpharadar = alpharadar + 360;
		}
		if (alpharadar > 360) {
			alpharadar = alpharadar - 360;
		}
		
		double a;
		double b;

		// equation de la droite de vision du capteur
		a = Math.tan(alpharadar * Math.PI / 180);
		b = (yRobot - a * xRobot);

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
		yintersection4 = b;// bord gauche, axe des ordonn�es

		// d1 : distance to top edge if defined (/!\ bottom edge in graphic window)
		// d2 : distance to bottom edge if defined (/!\ top edge in graphic window)
		// d3 : distance to right edge if defined
		// d4 : distance to left edge if defined
		if (0 <= xintersection1 && xintersection1 <= mapWidth) {
			d1 = Math.sqrt(Math.pow(mapHeight - yrobot, 2) + Math.pow(xrobot - xintersection1, 2));
		} else
			d1 = -1;
		if (0 <= xintersection2 && xintersection2 <= mapWidth) {
			d2 = Math.sqrt(Math.pow(yrobot, 2) + Math.pow(xrobot - xintersection2, 2));
		} else
			d2 = -1;
		if (0 <= yintersection3 && yintersection3 <= mapHeight) {
			d3 = Math.sqrt(Math.pow(yintersection3 - yrobot, 2) + Math.pow(mapWidth - xrobot, 2));
		} else
			d3 = -1;
		if (0 <= yintersection4 && yintersection4 <= mapHeight) {
			d4 = Math.sqrt(Math.pow(yintersection4 - yrobot, 2) + Math.pow(xrobot, 2));
		} else
			d4 = -1;

		//The "or" conditions corresponds to cases where the captor points exactly an angle, what produces some bugs.
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
		a = Math.tan(alpharadar * Math.PI / 180);
		b = (yRobot - a * xRobot);
		
		double A = 1 + a;
		double B = 2 * (-cx + a * b - a * cy);
		double C = cx * cx + cy * cy + b * b - 2 * b * cy - r * r;
		double delta = B * B - 4 * A * C;

		if (delta > 0) {
			double x = (-B - Math.cbrt(delta)) / (2 * A);
			double y = a * x + b;
			lst.add(x);
			lst.add(y);

			x = (-B + Math.cbrt(delta)) / (2 * A);
			y = a * x + b;
			lst.add(x);
			lst.add(y);

		} else if (delta == 0) {
			double x = -B / (2 * A);
			double y = a * x + b;

			lst.add(x);
			lst.add(y);
		}

		return lst;
	}

}
