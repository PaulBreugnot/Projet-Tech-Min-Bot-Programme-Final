package qLearning.model;

import java.util.ArrayList;
import java.util.Hashtable;

import commonInterface.Robot;

public class Reward {
	private int value;
	private static Robot titi;
	private static Hashtable<StateActionPair, Integer> HashReward = new Hashtable<>();

	public Reward(int value) {
		this.value = value;
	}

	public Reward(StateActionPair stateActionPair) {
		if (HashReward.get(stateActionPair) == null) {
			setNewReward(stateActionPair);
		}
		value = HashReward.get(stateActionPair);
	}

	private void setNewReward(StateActionPair stateActionPair) {
		// Action A = stateActionPair.getAction();
		HashReward.put(stateActionPair, rewardSystem8(stateActionPair));
	}

	public int rewardSystem1(ArrayList<DiscretisedState.RadarStates> radarStates) {
		boolean obstacle = false;
		for (DiscretisedState.RadarStates state : radarStates) {
			if (state == DiscretisedState.RadarStates.S0) {
				obstacle = true;
			}
		}
		if (obstacle) {
			return -1;
		} else {
			return 1;
		}
	}

	public int rewardSystem2(ArrayList<DiscretisedState.RadarStates> radarStates) {
		boolean obstacle = false;
		for (DiscretisedState.RadarStates state : radarStates) {
			if (state == DiscretisedState.RadarStates.S0) {
				obstacle = true;
			}
		}
		if (obstacle) {
			return -200;
		} else {
			int reward = 0;
			for (DiscretisedState.RadarStates state : radarStates) {
				switch (state) {
				case S1:
					reward += 5;
					break;
				case S2:
					reward += 10;
					break;

				case S3:
					reward += 15;
					break;

				case S4:
					reward += 20;
					break;

				case S5:
					reward += 30;
					break;

				case S6:
					reward += 40;
					break;

				case S7:
					reward += 55;
					break;

				case S8:
					reward += 60;
					break;

				case S9:
					reward += 80;
					break;

				case S10:
					reward += 100;
					break;

				default:
					break;
				}
			}
			return reward;
		}
	}

	public int rewardSystem3(StateActionPair stateActionPair) {
		ArrayList<DiscretisedState.RadarStates> radarStates = ((DiscretisedState) stateActionPair.getState())
				.getRadarStates();
		ArrayList<Integer> captorRewards = new ArrayList<>();
		int finalReward = 0;
		for (DiscretisedState.RadarStates state : radarStates) {
			switch (state) {
			case S0:
				captorRewards.add(-200);
				break;
			case S1:
				captorRewards.add(5);
				break;
			case S2:
				captorRewards.add(10);
				break;

			case S3:
				captorRewards.add(15);
				break;

			case S4:
				captorRewards.add(20);
				break;

			case S5:
				captorRewards.add(30);
				break;

			case S6:
				captorRewards.add(40);
				break;

			case S7:
				captorRewards.add(55);
				break;

			case S8:
				captorRewards.add(60);
				break;

			case S9:
				captorRewards.add(80);
				break;

			case S10:
				captorRewards.add(100);
				break;

			default:
				break;
			}
		}
		for (int r : captorRewards) {
			finalReward += r;
		}
		return finalReward;
	}

	public int rewardSystem4(ArrayList<DiscretisedState.RadarStates> radarStates) {
		// /!\ Only 3 states
		ArrayList<Integer> captorRewards = new ArrayList<>();
		int finalReward = 0;
		for (int i = 0; i < radarStates.size(); i++) {
			DiscretisedState.RadarStates state = radarStates.get(i);
			switch (state) {
			case S0:
				switch (i) {
				case 0:
					captorRewards.add(-5);
					break;
				case 1:
					captorRewards.add(-10);
					break;
				case 2:
					captorRewards.add(-20);
					break;
				case 3:
					captorRewards.add(-10);
					break;
				case 4:
					captorRewards.add(-5);
					break;
				}
				break;
			case S1:
				switch (i) {
				case 0:
					captorRewards.add(5);
					break;
				case 1:
					captorRewards.add(10);
					break;
				case 2:
					captorRewards.add(20);
					break;
				case 3:
					captorRewards.add(10);
					break;
				case 4:
					captorRewards.add(5);
					break;
				}
				break;
			case S2:
				switch (i) {
				case 0:
					captorRewards.add(10);
					break;
				case 1:
					captorRewards.add(20);
					break;
				case 2:
					captorRewards.add(50);
					break;
				case 3:
					captorRewards.add(20);
					break;
				case 4:
					captorRewards.add(10);
					break;
				}
				break;
			default:
				break;
			}
		}
		for (int r : captorRewards) {
			finalReward += r;
		}
		return finalReward;
	}

	public int rewardSystem5(ArrayList<DiscretisedState.RadarStates> radarStates) {
		// /!\ Only 3 states
		ArrayList<Integer> captorRewards = new ArrayList<>();
		int finalReward = 0;
		for (int i = 0; i < radarStates.size(); i++) {
			DiscretisedState.RadarStates state = radarStates.get(i);
			switch (state) {
			case S0:
				switch (i) {
				case 0:
					captorRewards.add(-20);
					break;
				case 1:
					captorRewards.add(-10);
					break;
				case 2:
					captorRewards.add(-5);
					break;
				case 3:
					captorRewards.add(-10);
					break;
				case 4:
					captorRewards.add(-20);
					break;
				}
				break;
			case S1:
				switch (i) {
				case 0:
					captorRewards.add(20);
					break;
				case 1:
					captorRewards.add(10);
					break;
				case 2:
					captorRewards.add(5);
					break;
				case 3:
					captorRewards.add(10);
					break;
				case 4:
					captorRewards.add(20);
					break;
				}
				break;
			case S2:
				switch (i) {
				case 0:
					captorRewards.add(50);
					break;
				case 1:
					captorRewards.add(20);
					break;
				case 2:
					captorRewards.add(10);
					break;
				case 3:
					captorRewards.add(20);
					break;
				case 4:
					captorRewards.add(50);
					break;
				}
				break;
			default:
				break;
			}
		}
		for (int r : captorRewards) {
			finalReward += r;
		}
		return finalReward;
	}

	public int rewardSystem6(StateActionPair stateActionPair) {
		ArrayList<DiscretisedState.RadarStates> radarStates = ((DiscretisedState) stateActionPair.getState())
				.getRadarStates();
		ArrayList<Integer> captorRewards = new ArrayList<>();
		int finalReward = 0;
		for (int i = 0; i < radarStates.size(); i++) {
			DiscretisedState.RadarStates state = radarStates.get(i);
			if (state != DiscretisedState.RadarStates.S10) {
				double h = Math.abs(DiscretisedState.value(state)
						* Math.sin(90 - Math.abs(titi.getRobotCaptors().get(i).getCaptorOrientation())));
				captorRewards
						.add((int) (Math.floor(100 * DiscretisedState.value(DiscretisedState.discretized1(h))) - 50));
			} else {
				captorRewards.add(100);
			}
		}
		for (int r : captorRewards) {
			finalReward += r;
		}
		return finalReward;
	}

	public int getValue() {
		return value;
	}

	public int rewardSystem7(ArrayList<DiscretisedState.RadarStates> radarStates) {
		// /!\ Only 3 states
		ArrayList<Integer> captorRewards = new ArrayList<>();
		int finalReward = 0;
		for (int i = 0; i < radarStates.size(); i++) {
			DiscretisedState.RadarStates state = radarStates.get(i);
			if (state != DiscretisedState.RadarStates.S10) {
				double h = Math.abs(DiscretisedState.value(state)
						* Math.sin(90 - Math.abs(titi.getRobotCaptors().get(i).getCaptorOrientation())));
				captorRewards.add((int) ((Math.abs(i - 2) + 1)
						* (Math.floor(150 * DiscretisedState.value(DiscretisedState.discretized2(h))) - 50)));
			} else {
				captorRewards.add(100);
			}
			if (i == 4) {
				System.out.println("State : " + state);
				System.out.println(captorRewards.get(i));
			}
		}
		for (int r : captorRewards) {
			finalReward += r;
		}
		return finalReward;
	}

	public static void setRobot(Robot titi) {
		Reward.titi = titi;
	}

	public int rewardSystem8(StateActionPair stateActionPair) {
		ArrayList<DiscretisedState.RadarStates> radarStates = ((DiscretisedState) stateActionPair.getState())
				.getRadarStates();
		ArrayList<Integer> captorRewards = new ArrayList<>();
		int finalReward = 0;
		for (int i = 0; i < radarStates.size(); i++) {
			DiscretisedState.RadarStates state = radarStates.get(i);
			if (state != DiscretisedState.RadarStates.S10) {
				//captorRewards.add((int) ((3 - Math.abs(i - 2)) * (100 * DiscretisedState.value(state) - 25)));
				captorRewards.add((int) ((100 * DiscretisedState.value(state) - 25)));
			} else {
				//captorRewards.add(100);
				captorRewards.add(50);
			}
		}
		for (int r : captorRewards) {
			finalReward += r;
		}
		return finalReward;
	}

	public int rewardSystem9(StateActionPair stateActionPair) {
		ArrayList<DiscretisedState.RadarStates> radarStates = ((DiscretisedState) stateActionPair.getState())
				.getRadarStates();
		ArrayList<Integer> captorRewards = new ArrayList<>();
		for (DiscretisedState.RadarStates state : radarStates) {
			switch (state) {
			case S0:
				captorRewards.add(-200);
				break;
			case S1:
				captorRewards.add(-100);
				break;
			case S2:
				captorRewards.add(-50);
				break;

			case S3:
				captorRewards.add(0);
				break;

			case S4:
				captorRewards.add(20);
				break;

			case S5:
				captorRewards.add(40);
				break;

			case S6:
				captorRewards.add(60);
				break;

			case S7:
				captorRewards.add(80);
				break;

			case S8:
				if (stateActionPair.getAction().getValue() == DiscretisedAction.Actions.GO_FORWARD) {
					captorRewards.add(110);
				} else {
					captorRewards.add(100);
				}
				break;

			case S9:
				if (stateActionPair.getAction().getValue() == DiscretisedAction.Actions.GO_FORWARD) {
					captorRewards.add(130);
				} else {
					captorRewards.add(120);
				}
				break;

			case S10:
				if (stateActionPair.getAction().getValue() == DiscretisedAction.Actions.GO_FORWARD) {
					captorRewards.add(210);
				} else {
					captorRewards.add(200);
				}
				break;

			default:
				break;
			}
		}
		int finalReward = Integer.MAX_VALUE;
		for (int r : captorRewards) {
			if (r < finalReward) {
				finalReward = r;
			}
		}
		/*
		 * int finalReward = 0; for (int r : captorRewards) { finalReward += r; }
		 */

		if (stateActionPair.getAction().getValue() == DiscretisedAction.Actions.GO_FORWARD) {
			finalReward += 0;
		}
		return finalReward;
	}

	public int rewardSystem10(StateActionPair stateActionPair) {
		ArrayList<DiscretisedState.RadarStates> radarStates = ((DiscretisedState) stateActionPair.getState())
				.getRadarStates();
		ArrayList<Integer> captorRewards = new ArrayList<>();
		for (int i = 0; i < radarStates.size(); i++) {
			DiscretisedState.RadarStates state = radarStates.get(i);
			switch (state) {
			case S0:
				captorRewards.add(-200);
				break;
			case S1:
				captorRewards.add(-100);
				break;
			case S2:
				if (i == 4) {
					captorRewards.add(200);
				}
				else {
				captorRewards.add(-50);
				}
				break;

			case S3:
				if (i == 4) {
					captorRewards.add(-100);
				} else {
					captorRewards.add(0);
				}
				break;

			case S4:
				if (i == 4) {
					captorRewards.add(-100);
				} else {
					captorRewards.add(20);
				}
				break;

			case S5:
				if (i == 4) {
					captorRewards.add(-100);
				} else {
					captorRewards.add(40);
				}
				break;

			case S6:
				if (i == 4) {
					captorRewards.add(-100);
				} else {
					captorRewards.add(60);
				}
				break;

			case S7:
				if (i == 4) {
					captorRewards.add(-100);
				} else {
					captorRewards.add(80);
				}
				break;

			case S8:
				if (i == 4) {
					captorRewards.add(-100);
				} else {
					captorRewards.add(100);
				}
				break;

			case S9:
				if (i == 4) {
					captorRewards.add(-100);
				} else {
					captorRewards.add(120);
				}
				break;

			case S10:
				if (i == 4) {
					captorRewards.add(-100);
				} else {
					captorRewards.add(200);
				}
				break;

			default:
				break;
			}
		}

		int finalReward = 0;
		for (int r : captorRewards) {
			finalReward += r;
		}

		if (stateActionPair.getAction().getValue() == DiscretisedAction.Actions.GO_FORWARD) {
			finalReward += 0;
		}
		return finalReward;
	}
}
