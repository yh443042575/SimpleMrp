package edu.hit.mrp.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.hit.mrp.bean.ProductsComponent;
import edu.hit.mrp.dao.ProductsComponentDao;

public class DateCalculator {
	
	private List<String> stringResult=new ArrayList<String>();
	private List<Prepare> result=new ArrayList<Prepare>();
	

	
	private ProductsComponentDao productsComponentDao=new ProductsComponentDao();
	
	public void crateResult(List<ProductsComponent> solution,int deadline,int parentID){
		
		boolean hasChild=false;
		if(solution.size()!=0){
			for(ProductsComponent pro:solution)
			{
				if(pro.getParentID()==parentID)
				{
					hasChild=true;
					this.result.add(new Prepare(deadline,pro.getComponentCode(),pro.getCount()));
					crateResult(solution,deadline-pro.getProjectTimeLimit(),pro.getID());
					
				}
			}
		if(hasChild==false)
		{
			for(ProductsComponent pro:solution)
				if(pro.getrID()==parentID){
					this.result.add(new Prepare(deadline,pro.getComponentCode()+"的原料",pro.getCount()));
				}
			
		}
		}
		
		else {
			System.out.println("方案缺失");
			return;
		}
	}
	/**
	 * 通过给定方案与最后期限，算出该到那个时间点应该出哪些货物
	 * @param solution
	 * @param deadline
	 * @return
	 */
	public List<String> getResult(String solutionName,int deadline,int count){
		try {
			this.crateResult(productsComponentDao.getSolution(solutionName), deadline, 0);
			Collections.sort(result);
			for(Prepare p:result)
			{
				
				if(p.preparation.matches("[a-zA-z]?"))
					stringResult.add("第"+p.deadline+"天，需要生产出"+count*p.unit+"个"+p.preparation);
				else
					stringResult.add("第"+p.deadline+"天，需要准备出"+count*p.unit+"个"+p.preparation);
			}
			return stringResult;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.stringResult;
	}

	
	class Prepare implements Comparable{
		@Override
		public int compareTo(Object o) {
			if(o instanceof Prepare){
				Prepare p=(Prepare)o;
				if(this.deadline>p.deadline)
					return -1;
				else if(this.deadline==p.deadline)
					return 0;
				else if(this.deadline<p.deadline)
					return 1;	
			}
			System.out.println("不可比较");
			return 0;
		}
		int deadline;
		int unit;
		String preparation;
		public Prepare(int deadline, String preparation,int unit) {
			this.deadline = deadline;
			this.preparation = preparation;
			this.unit=unit;
		}
		
		
		
	}
}
