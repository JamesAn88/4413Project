package com.foodrus.dao.jdbp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import com.foodrus.bean.vo.Item;
import com.foodrus.dao.RowMapper;
import com.foodrus.util.DaoHelper;


public class ItemDao extends GenericDaoJdbc<Item> {
	// *** fields used by this DAO
	public static final String SELECT_ITEM_BY_ID = "select * from roumani.item where number = ?";
	public static final String SELECT_ALL_ITEMS = "select * from roumani.item";
	public static final String SELECT_ITEMS_WITH_KYEWORD = "select * from roumani.item "
			+ "where upper(name) like upper(?)";
	public static final String SELECT_ITEMS_BY_CAT = "select * from roumani.item where catid = ?";
	
	public static final RowMapper<Item> ITEM_ROW_MAPPER = new RowMapper<Item>(){
		@Override
		public Item mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			Item item = new Item();
			item.setCatId(resultSet.getInt("CatId"));
			item.setCostPrice(resultSet.getDouble("CostPrice"));
			item.setName(resultSet.getString("name"));
			item.setNumber(resultSet.getString("number"));
			item.setOnOrder(resultSet.getInt("OnOrder"));
			item.setPrice(resultSet.getDouble("price"));
			item.setQty(resultSet.getInt("qty"));
			item.setReOrder(resultSet.getInt("reOrder"));
			item.setSupId(resultSet.getInt("supId"));
			item.setUnit(resultSet.getString("unit"));
			return item;
		}
	};
	
	/**
	 * Default constructor
	 * @throws NamingException
	 */
	public ItemDao() throws NamingException {
		super(Item.class);
	}

	public ItemDao(DataSource dataSource){
		super(Item.class, dataSource);
	}
	
	@Override
	public Item get(Object... pks) throws SQLException {
		Connection conn = getDataSource().getConnection();
		Map<Integer, Object> params = new HashMap<>();
		if(pks != null){
			params.put(1, pks[0]);
		}
		Item item = (Item) DaoHelper.queryForFirstResult(SELECT_ITEM_BY_ID, params, conn, ITEM_ROW_MAPPER);
		conn.close();
		return item;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Item> getAll() throws SQLException {
		Connection conn = getDataSource().getConnection();
		List<Item> items = (List<Item>) DaoHelper.queryForList(SELECT_ALL_ITEMS, conn, ITEM_ROW_MAPPER);
		conn.close();
		return items;
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getItemsWithKeyWord(String keyWord) throws SQLException{
		Map<Integer, Object> params = new HashMap<>();
		if(keyWord != null){
			params.put(1, "%"+keyWord+"%");
		}
		Connection conn = getDataSource().getConnection();
		List<Item> items = (List<Item>) DaoHelper.queryForList(SELECT_ITEMS_WITH_KYEWORD, 
				params, conn, ITEM_ROW_MAPPER);
		conn.close();
		return items;
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getItemsByCatid(String catid) throws Exception{
		Map<Integer, Object> params = new HashMap<>();
		
		if (catid != null){
			params.put(1, new Integer(Integer.parseInt(catid)));
		}
		Connection conn = getDataSource().getConnection();
		List<Item> items = (List<Item>) DaoHelper.queryForList(SELECT_ITEMS_BY_CAT, 
				params, conn, ITEM_ROW_MAPPER);
		conn.close();
		return items;
	}
}