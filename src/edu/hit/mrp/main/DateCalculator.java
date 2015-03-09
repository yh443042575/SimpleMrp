package edu.hit.mrp.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import edu.hit.mrp.bean.ProductsComponent;
import edu.hit.mrp.dao.ProductsComponentDao;
import edu.hit.mrp.dao.VacationDao;

public class DateCalculator {

	private List<String> stringResult = new ArrayList<String>();
	private List<Prepare> result = new ArrayList<Prepare>();
	private VacationDao vacationDao = new VacationDao();

	private ProductsComponentDao productsComponentDao = new ProductsComponentDao();

	public void crateResult(List<ProductsComponent> solution, int deadline,
			int parentID) {

		boolean hasChild = false;
		if (solution.size() != 0) {
			for (ProductsComponent pro : solution) {
				if (pro.getParentID() == parentID) {
					hasChild = true;
					this.result.add(new Prepare(deadline, pro
							.getComponentCode(), pro.getCount()));
					crateResult(solution, deadline - pro.getProjectTimeLimit(),
							pro.getID());

				}
			}
			if (hasChild == false) {
				for (ProductsComponent pro : solution)
					if (pro.getrID() == parentID) {
						this.result.add(new Prepare(deadline, pro
								.getComponentCode()
								+ "��ԭ��", pro.getCount()));
					}

			}
		}

		else {
			System.out.println("����ȱʧ");
			return;
		}
	}

	/**
	 * ͨ������������������ޣ�����õ��Ǹ�ʱ���Ӧ�ó���Щ����
	 * 
	 * @param solution
	 * @param deadline
	 * @return
	 */
	public List<String> getResult(String solutionName, String deadline,
			int count) {
		
		try {
			int workDays=getWorkDays(deadline);
			List<String> dateList =this.getDateList(deadline,workDays);
			
			this.crateResult(productsComponentDao.getSolution(solutionName),
					getWorkDays(deadline), 0);
			Collections.sort(result);
			
			
			
			for (Prepare p : result) {

				if (p.preparation.matches("[a-zA-z]?"))
					stringResult.add(dateList.get(workDays-p.deadline)+ "����Ҫ������" + count
							* p.unit + "��" + p.preparation);
				else
					stringResult.add(dateList.get(workDays-p.deadline)+ "����Ҫ׼����" + count
							* p.unit + "��" + p.preparation);
			}
			return stringResult;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.stringResult;
	}

	/**
	 * ���ݸ�����deadline�������컹�ж�����Ч������
	 * 
	 * @param deadline
	 * @return
	 */
	private int getWorkDays(String deadline) throws Exception {
		int allDays;
		int vacations;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date deadLineDate = sdf.parse(deadline);
		Date now = new Date();
		allDays = daysBetween(sdf.parse(sdf.format(now)), deadLineDate);

		return allDays - vacationDao.getVacationCount();

	}

	/**
	 * ������������֮����������
	 * 
	 * @param smdate
	 *            ��С��ʱ��
	 * @param bdate
	 *            �ϴ��ʱ��
	 * @return �������
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	
	/**
	 * �õ�����deadline��ȥ�����ڵĹ�������
	 * @param deadLine
	 * @param day
	 * @return
	 * @throws Exception
	 */
	private List<String> getDateList(String deadLine, int day) throws Exception {

		List<String> dateList =new ArrayList<String>();
		List<String> vacationList = vacationDao.getVacationList(deadLine);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s;
		
		Date deadLineDate = sdf.parse(deadLine);
		Calendar cal = Calendar.getInstance();
		cal.setTime(deadLineDate);
		long  time = cal.getTimeInMillis();
		for (int i = 0; i < day; i++) {
			cal.setTimeInMillis(time-1000L*3600*24*i);
			s=sdf.format(cal.getTime());
			if(!vacationList.contains(s)){
				dateList.add(s);
			}		
		}
		return dateList;
	}

	class Prepare implements Comparable {
		@Override
		public int compareTo(Object o) {
			if (o instanceof Prepare) {
				Prepare p = (Prepare) o;
				if (this.deadline > p.deadline)
					return -1;
				else if (this.deadline == p.deadline)
					return 0;
				else if (this.deadline < p.deadline)
					return 1;
			}
			System.out.println("���ɱȽ�");
			return 0;
		}

		int deadline;
		int unit;
		String preparation;

		public Prepare(int deadline, String preparation, int unit) {
			this.deadline = deadline;
			this.preparation = preparation;
			this.unit = unit;
		}

	}
}
