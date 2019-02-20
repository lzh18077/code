package com.zs.pms.serviceimpl;

import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zs.pms.dao.UserDao;
import com.zs.pms.exception.AppException;
import com.zs.pms.po.TPermission;
import com.zs.pms.po.TUser;
import com.zs.pms.service.UserService;
import com.zs.pms.utils.Contants;
import com.zs.pms.utils.MD5;
import com.zs.pms.vo.QueryUser;

@Service
@Transactional //需要开启事务的业务对象
public class UserServiceImpl implements UserService{
	@Autowired
	UserDao dao;
	@Override
	public void hello() {
		// TODO Auto-generated method stub
		System.out.println("hello Spring");
	}
	@Override
	public List<TPermission> queryByUid(int id){
		return dao.queryByUid(id);
	}
	public List<TPermission> genMenu(List<TPermission> pers){
		//创建新容器
		List<TPermission> list=new ArrayList<>();
		//遍历权限列表
		for(TPermission per:pers) {
			//一级菜单
			if (per.getLev()==1) {
				//加载该一级菜单下的二级菜单
				//遍历
				for(TPermission per2:pers) {
					//二级权限的上级id等于一级权限id
					if (per2.getPid()==per.getId()) {
						//添加子权限
						per.addChild(per2);
					}
				}
				//加到新的集合中
				list.add(per);
			}
		}
		return list;
	}
	//查询
	@Override
	public List<TUser> queryByCon(QueryUser query) {
		// TODO Auto-generated method stub
		return dao.queryByCon(query);
	}
	//修改
	@Override
	public void update(TUser user) {
		// TODO Auto-generated method stub
		dao.update(user);
	}
	//批量删除
	@Override
	public void deleteByIds(int[] ids) {
		// TODO Auto-generated method stub
		dao.deleteByIds(ids);
	}
	//新增
	@Override
	//该方法只要有异常就会回滚事务
	@Transactional(rollbackFor=Exception.class)
	public int insertUser(TUser user) throws AppException{
		//模拟业务异常
		if ("admin".equals(user.getLoginname())) {
			throw new AppException(1003,"登录名 不能为admin");
		}
		MD5 md5=new MD5();
		user.setPassword(md5.getMD5ofStr(user.getPassword()));
		// TODO Auto-generated method stub
		dao.insertUser(user);
//		int a=1/0;//产生异常
//		dao.insertUser(user);
		//返回主键
		return user.getId();
	}
	//删除
	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}
	//获得分页记page：当前页 query：查询条件
	@Override
	public List<TUser> queryByPage(int page, QueryUser query) {
		// TODO Auto-generated method stub
		//通过当前页数计算起始数和截止数
		//起始数从1 开始
		int start =(page-1)*Contants.PAGECONT+1;
		//截止数
		int end=page*Contants.PAGECONT;
		query.setStart(start);
		query.setEnd(end);
		return dao.queryByPage(query);
	}
	//计算总页数
	@Override
	public int queryPageCont(QueryUser query) {
		// TODO Auto-generated method stub
		//通过总条数计算总页数
		int count=dao.queryCount(query);
		//能整除
		if (count%Contants.PAGECONT==0) {
			return count/Contants.PAGECONT;
		}else {
			return count/Contants.PAGECONT+1;
		}
	}
	@Override
	public TUser queryById(int id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}
}