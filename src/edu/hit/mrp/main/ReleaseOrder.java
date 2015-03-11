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
	String[] component = { "P", "O", "R" };// 方桌方案的基本组件
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
	 * 保存订单为执行计划后，根据订单内的基础组件情况更新组件的库存与在途量
	 * 
	 * @throws Exception
	 */

	public void release() throws Exception {

		List<OrderBean> orderBeanList;

		orderBeanList = this.getOrderBeans(mrpMain.resultTextArea.getText());

		// 若本次下达计划于上次下达计划没有其他的生产任务，则将库存量更新到本次下达计划日之前一天
		stockTransitDao.flashStockAndTransit(orderBeanList);
		// 根据库存量与批量，将实际需要生产的构件数写入方案中，并更新交付基本组件日期时的库存
		for (OrderBean o : orderBeanList)
			flashExecutePlan(o);
	}

	private void flashExecutePlan(OrderBean orderBean) throws Exception {

		// 得到基础货物交付日期前一天的库存
		StockTransitBean stockTransitBean = stockTransitDao
				.getRecentlyComponent(orderBean.getMaterial(), sdf
						.format(stockTransitDao.getPreviousDay(orderBean
								.getDate(), 1)));
		// 得到该基础控件的生产批量
		int batch = productsComponentDao.getBatch(orderBean.getMaterial());

		if ((orderBean.getCount() - stockTransitBean.getInStock()) < batch) {// 若当前需求量减去库存没有物件批量多，则生产批量零件，写入产品方案，更新库存

			orderDao.insertSubPlan(stockTransitDao.getPreviousDay(orderBean
					.getDate(), productsComponentDao
					.getProjectTimeLimit(orderBean.getMaterial())), orderBean
					.getOrderName(), "采购" + orderBean.getMaterial(), batch);
			stockTransitDao.insertStockTransit(orderBean.getDate(), orderBean
					.getMaterial(), batch + stockTransitBean.getInStock()
					- orderBean.getCount(), batch);
		} else {
			orderDao.insertSubPlan(stockTransitDao.getPreviousDay(orderBean
					.getDate(), productsComponentDao
					.getProjectTimeLimit(orderBean.getMaterial())), orderBean
					.getOrderName(), "采购" + orderBean.getMaterial(), orderBean
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
					String s1[] = ss2.split("，");
					Date date = sdf.parse(s1[0]);

					System.out.println(sdf.format(date));

					String s2[] = s1[1].split("个");
					String material = s2[1];

					int count = Integer.parseInt(s2[0].split("出")[1]);
					orderBeanList.add(new OrderBean(date, orderName, material,
							count));
				}

			}
		return orderBeanList;
	}

}
