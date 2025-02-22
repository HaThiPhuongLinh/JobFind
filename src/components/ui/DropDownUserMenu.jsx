import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { logout } from "../../redux/slices/authSlice";
import { useDispatch } from "react-redux";
import itemsUserMenu from "../../data/itemsUserMenu";
import { faRightFromBracket } from "@fortawesome/free-solid-svg-icons";
import PropTypes from "prop-types";

const DropDownUserMenu = ({ user }) => {
  const dispatch = useDispatch();

  console.log(user);
  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <div className="font-norma">
      {/* header */}
      <div className="flex items-center pb-4">
        <div className="pe-4">
          <img src="" alt="logo" />
        </div>
        <div>
          <p className="font-bold text-lg">Nhật Nguyễn</p>
          <p>
            {" "}
            <span className="text-slate-500">Mã ứng viên:</span> #656231
          </p>
        </div>
      </div>
      {/* body */}
      <div>
        {itemsUserMenu.map((item) => (
          <div
            key={item.title}
            className="flex items-center py-4 px-4 rounded-lg mb-4 bg-slate-100"
          >
            <FontAwesomeIcon
              icon={item.icon}
              className="pe-4 text-lg text-primary"
            />
            <p>{item.title}</p>
          </div>
        ))}

        {/* Đăgn xuất */}
        <div
          className="flex items-center py-4 px-4 rounded-lg mb-4 bg-slate-100"
          onClick={handleLogout}
        >
          <FontAwesomeIcon
            icon={faRightFromBracket}
            className="pe-4 text-lg text-primary"
          />
          <p className="text-red-500">Đăng xuất</p>
        </div>
      </div>
    </div>
  );
};
DropDownUserMenu.propTypes = {
  user: PropTypes.object.isRequired,
};

export default DropDownUserMenu;
