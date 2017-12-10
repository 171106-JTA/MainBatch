package revature.trms.java;

import revature.trms.dao.DaoImpl;

public class Function {
	
	public double trms(double uc, double sem, double cpc, double cert, double tt, double other, String emp_id){
		DaoImpl dao = new DaoImpl();
		double rem = ((uc*.8) + (sem*.6) + (cpc*.75) + cert + (tt*.9) + (other*.3));
		double available  = dao.getAvail(emp_id);
		
		if(available - rem < 0)
			return available;
		else
			return available - rem;
	}

}
