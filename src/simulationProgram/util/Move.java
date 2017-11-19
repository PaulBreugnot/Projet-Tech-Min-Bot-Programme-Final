package simulationProgram.util;

import simulationProgram.simRobot.SimRobot;

public abstract class Move {
	public Move(SimRobot titi){
		// angles en rad ou rad/s
		this(titi, 10);
	}
	// 2 constructeur si on veut presiser le pas de temps
		public Move(SimRobot titi,int delta){
			// angles en rad ou rad/s	
			titi.setAlphaOrientation(titi.getAlphaOrientation()+titi.getWheelRadius()/2/titi.getRobotSize()*(titi.getV1()+titi.getV2()));
			
			titi.setXPos(titi.getXPos()+titi.getWheelRadius()/2*(titi.getV1()+titi.getV2())*Math.cos(titi.getAlphaOrientation())*delta/1000);//angle de départ, possibilité de limiter les erreurs en prenant une moyenne ou en rajoutant des pas
			
			titi.setYPos(titi.getYPos()+titi.getWheelRadius()/2*(titi.getV1()+titi.getV2())*Math.sin(titi.getAlphaOrientation())*delta/1000);
			
		
	}
}
