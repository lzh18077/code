package com.zs.pms.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zs.pms.exception.AppException;
import com.zs.pms.po.TDep;
import com.zs.pms.po.TPermission;
import com.zs.pms.po.TUser;
import com.zs.pms.service.UserService;
import com.zs.pms.vo.QueryUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationcontext.xml")
public class TestUserService {
	@Autowired
	UserService us;
	//测试
	public void testHello() {
		us.hello();
	}
	//测试
	public void testLogin() {
		List<TPermission> list1=us.queryByUid(3084);
		for(TPermission per:list1) {
			System.out.println(per.getPname());
		}
		System.out.println("-----------------整理后的-----------------");
		for(TPermission per1:us.genMenu(list1)) {
			//一级权限
			System.out.println(per1.getPname());
			for(TPermission per2:per1.getChildren()) {
				System.out.println("------"+per2.getPname());
			}
		}
	}
	//查询测试
	public void testQuery() {
		//创建查询对象
		QueryUser query=new QueryUser();
		query.setLoginname("lzh");
		query.setPassword("lzh");
		query.setSex("女");
		System.out.println(us.queryByCon(query).size());
	}
	//批量删除测试
	public void testDeletes() {
		int [] ad= {3103,3086};
		us.deleteByIds(ad);
	}
	//修改测试
	public void testUpdate() {
		TUser user=new TUser();
		user.setId(3162);
		user.setLoginname("test111");
		user.setPassword("1234qqq");
		user.setSex("女");
		user.setBirthday(new Date());
		user.setEmail("update@qq.com");
		TDep dept=new TDep();
		dept.setId(6);
		user.setDept(dept);
		user.setRealname("新增");
		user.setUpdator(2000);
		user.setIsenabled(1);
		user.setIsenabled(1);
		us.update(user);
	}
	//新增测试
	@Test
		public void testInsert() throws AppException {
			TUser user=new TUser();
			TDep dept=new TDep();
			user.setLoginname("asd");
			user.setPassword("1234eee");
			user.setSex("男");
			user.setBirthday(new Date());
			user.setEmail("insert@qq.com");
			dept.setId(7);
			user.setDept(dept);
			user.setRealname("新增");
			user.setCreator(3084);
			user.setIsenabled(1);
			System.out.println(us.insertUser(user));
		}
		//删除测试
		public void testDelete() {
			int id=1007;
			us.deleteById(id);
		}
		
		//分页和总条数测试
		public void tsetQuery() {
			//创建查询对象
			QueryUser query=new QueryUser();
			query.setSex("男");
			for(TUser user:us.queryByPage(1, query)) {
				System.out.println(user.getId()+" "+user.getLoginname());;
			}
			System.out.println("共"+us.queryPageCont(query)+"页");
		}
}
