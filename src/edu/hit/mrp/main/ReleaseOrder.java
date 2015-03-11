package edu.hit.mrp.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.hit.mrp.bean.OrderBean;
import edu.hit.mrp.bean.StockTransitBean;
import edu.hit.mrp.dao.OrderDao;
import edu.hit.mrp.dao.ProductsComponentDao;
import edu.hit.mrp.dao.StockTransitDao;

public class ReleaseOrder {

	String order;
	String orderName;
	String[] component = { "P", "O", "R" };// ���������Ļ������
	MrpMain mrpMain;
	OrderDao orderDao = new OrderDao();
	StockTransitDao stockTransitDao = new StockTransitDao();
	ProductsComponentDao productsComponentDao = new ProductsComponentDao();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public ReleaseOrder(String orderName, MrpMain mrpMain) {
		this.orderName = orderName;
		this.mrpMain = mrpMain;
	}

	/**
	 * ���涩��Ϊִ�мƻ��󣬸��ݶ����ڵĻ�����������������Ŀ������;��
	 * 
	 * @throws Exception
	 */

	public void release() throws Exception {

		List<OrderBean> orderBeanList;

		orderBeanList = this.getOrderBeans(mrpMain.resultTextArea.getText());

		// �������´�ƻ����ϴ��´�ƻ�û�����������������򽫿�������µ������´�ƻ���֮ǰһ��
		stockTransitDao.flashStockAndTransit(orderBeanList);
		// ���ݿ��������������ʵ����Ҫ�����Ĺ�����д�뷽���У������½��������������ʱ�Ŀ��
		for (OrderBean o : orderBeanList)
			flashExecutePlan(o);
	}

	private void flashExecutePlan(OrderBean orderBean) throws Exception {

		// �õ��������ｻ������ǰһ��Ŀ��
		StockTransitBean stockTransitBean = stockTransitDao
				.getRecentlyComponent(orderBean.getMaterial(), sdf
						.format(stockTransitDao.getPreviousDay(orderBean
								.getDate(), 1)));
		// �õ��û����ؼ�����������
		int batch = productsComponentDao.getBatch(orderBean.getMaterial());

		if ((orderBean.getCount() - stockTransitBean.getInStock()) < batch) {// ����ǰ��������ȥ���û����������࣬���������������д���Ʒ���������¿��

			orderDao.insertSubPlan(stockTransitDao.getPreviousDay(orderBean
					.getDate(), productsComponentDao
					.getProjectTimeLimit(orderBean.getMaterial())), orderBean
					.getOrderName(), "�ɹ�" + orderBean.getMaterial(), batch);
			stockTransitDao.insertStockTransit(orderBean.getDate(), orderBean
					.getMaterial(), batch + stockTransitBean.getInStock()
					- orderBean.getCount(), batch);
		} else {
			orderDao.insertSubPlan(stockTransitDao.getPreviousDay(orderBean
					.getDate(), productsComponentDao
					.getProjectTimeLimit(orderBean.getMaterial())), orderBean
					.getOrderName(), "�ɹ�" + orderBean.getMaterial(), orderBean
					.getCount());
			stockTransitDao.insertStockTransit(orderBean.getDate(), orderBean
					.getMaterial(), orderBean.getCount() + stockTransitBean.getInStock()
					- orderBean.getCount(), orderBean.getCount());

		}

	}

	private List<OrderBean> getOrderBeans(String order) throws Exception {
		List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] orderStrings = order.split("\n");
		for (String ss1 : component)
			for (String ss2 : orderStrings) {
				if (ss2.contains(ss1)) {
					String s1[] = ss2.split("��");
					Date date = sdf.parse(s1[0]);

					System.out.println(sdf.format(date));

					String s2[] = s1[1].split("��");
					String material = s2[1];

					int count = Integer.parseInt(s2[0].split("��")[1]);
					orderBeanList.add(new OrderBean(date, orderName, material,
							count));
				}

			}
		return orderBeanList;
	}

}
