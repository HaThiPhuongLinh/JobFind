import { Link } from "react-router-dom";
import logo from "../assets/logo.png";

// import data
import navItems from "../data/header_submenu";

const Header = () => {
  // console.log(navItems);

  return (
    <div className=" header flex justify-between items-center px-4 font-medium shadow">
      <div className="h-full">
        <img src={logo} alt="logo" className="h-full" />
      </div>

      <ul className="flex grow justify-start items-center px-3">
        {navItems.map((item, index) => (
          <li key={index} className="nav-tab ps-4 pe-6 relative group">
            <button className="">{item.title}</button>

            {/* Submenu khi hover vào tab */}
            <ul className="submenu-item absolute left-0 top-full opacity-0 invisible group-hover:opacity-100 group-hover:visible bg-white shadow-lg transition-all duration-200">
              {item.subItems.map((subItem, index) => (
                <li
                  key={index}
                  className="py-4 px-4 bg-slate-100 rounded-md mb-3"
                >
                  <Link>{subItem}</Link>
                </li>
              ))}
            </ul>
          </li>
        ))}
      </ul>

      <div className="button-group-login flex justify-end items-center">
        <Link
          to="/login"
          className="btn-login border-solid border-2 rounded-md px-4 py-2 ml-4"
        >
          Đăng nhập
        </Link>
        <Link
          to="/signup"
          className="btn-signup border-2 border-solid px-4 py-2 rounded-md ml-4 text-white"
        >
          Đăng ký
        </Link>
        <Link
          to="/"
          className="header-button border-2 border-solid px-4 py-2 rounded-md ml-4 text-white"
        >
          Đăng tuyển & tìm hồ sơ
        </Link>
      </div>
    </div>
  );
};

export default Header;
