package com.cnki.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cnki.demo.bean.User;

/**
 * 实现该接口，可自定义登陆验证
 * @author hs
 *
 */
public class BaseUserDetailService implements UserDetailsService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new  User();
		
		System.out.println(username);

		String sql = "select username,password,id from user where username=?";
		Object[] objs = { username };
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, objs);
		if (list.size() > 0) {
			String sql1 = "select ro.role_name name from user_role ur left join role ro on ur.rid=ro.id  where ur.uid=?";
			Object[] objs1 = { list.get(0).get("id")+"" };
			List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql1, objs1);
			String strRole = "";
			System.out.println("用户执行认证密码操作："+list2.toString()+list.toString());
			if(list2.size()>0){
				strRole = list2.get(0).get("name")+"";
			}
			List<GrantedAuthority> list1 = new ArrayList<>();
			list1.add(new SimpleGrantedAuthority(strRole));
			user.setUsername(username);
			user.setPassword("{noop}"+list.get(0).get("password"));
			user.setAuthorities(list1);
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setEnabled(true);
		}
		// TODO Auto-generated method stub
		return user;
	}
	
	
	
	
}
