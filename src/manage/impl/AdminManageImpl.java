package manage.impl;
import java.sql.SQLException;
import java.util.regex.Pattern;
import dao.AdminDao;
import dao.impl.AdminDaoImpl;
import entity.Admin;
import exception.AdminException;
import manage.AdminManage;
/**
 * @Description: 管理员业务实现
 * @author: 我的袜子都是洞
 * @date: 2019-01-19 14:36
 */
public class AdminManageImpl implements AdminManage {
	/**
	 * 管理员登陆验证
	 * @param admin
	 * @return
	 */
	@Override
	public boolean loginAdmin(String loginname, String password1,String password2) throws AdminException {
		if (loginname == null) {
			throw new AdminException("用户名为空");
		}
		if ("".equals(password1) || "".equals(password2)) {
			throw new AdminException("密码为空");
		}
		if (password1.equals(password2)) {
			throw new AdminException("两次密码不一致");
		}
		// 以字母开头，长度在6~18之间，只能包含字符、数字和下划线
		String pattern = "^[a-zA-Z]\\w{5,17}$";
		boolean isMatch = Pattern.matches(pattern, loginname);
		if (!isMatch) {
			throw new AdminException("用户名：以字母开头，长度在6~18之间，只能包含字符、数字和下划线");
		}
		AdminDao adminDao = new AdminDaoImpl();
		try {
			Admin admin = adminDao.getAdmin(loginname);
			if (admin.getPassword().equals(password1)) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new AdminException("你输入的帐号不存在");
		}
	}

}