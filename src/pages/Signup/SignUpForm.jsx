import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLock, faUser, faEnvelope } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import { faGoogle, faSquareFacebook } from "@fortawesome/free-brands-svg-icons";

import { useState } from "react";

const SignUpForm = () => {
  // Theo dõi trạng thái form
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <div className="mx-auto pt-6">
      <p className="text-2xl text-primary">Chào mừng bạn đến với FindJob</p>
      <p className="fotn-light text-gray-500 py-3">
        Cùng xây dựng một hồ sơ nổi bật và nhận được các cơ hội sự nghiệp lý
        tưởng
      </p>

      <form action="">
        {/* form group */}
        <div className="form-group pb-3">
          <label htmlFor="" className="block pb-1">
            Họ và tên
          </label>
          <div className="flex items-center border border-slate-300 rounded-lg p-2 ">
            <FontAwesomeIcon
              icon={faUser}
              className="text-primary text-xl pe-6"
            />
            <input
              type="text"
              name="name"
              placeholder="Nhập họ tên"
              required
              className="w-full outline-none"
              value={formData.name}
              onChange={handleChange}
            />
          </div>
        </div>

        {/* form group */}
        <div className="form-group pb-3">
          <label htmlFor="" className="block pb-1">
            Email
          </label>
          <div className="flex items-center border border-slate-300 rounded-lg p-2 ">
            <FontAwesomeIcon
              icon={faEnvelope}
              className="text-primary text-xl pe-6"
            />
            <input
              type="email"
              name="email"
              placeholder="Nhập email"
              required
              className="w-full outline-none"
              value={formData.email}
              onChange={handleChange}
            />
          </div>
        </div>

        {/* form group */}
        <div className="form-group pb-3">
          <label htmlFor="" className="block pb-1">
            Mật khẩu
          </label>
          <div className="flex items-center border border-slate-300 rounded-lg p-2 ">
            <FontAwesomeIcon
              icon={faLock}
              className="text-primary text-xl pe-6"
            />
            <input
              type="password"
              name="password"
              placeholder="Nhập mật khẩu"
              required
              className="w-full outline-none"
              value={formData.password}
              onChange={handleChange}
            />
          </div>
        </div>

        {/* form group */}
        <div className="form-group pb-3">
          <label htmlFor="" className="block pb-1">
            Xác nhận mật khẩu
          </label>
          <div className="flex items-center border border-slate-300 rounded-lg p-2 ">
            <FontAwesomeIcon
              icon={faLock}
              className="text-primary text-xl pe-6"
            />
            <input
              type="password"
              name="confirmPassword"
              placeholder="Nhập lại mật khẩu"
              required
              className="w-full outline-none"
              value={formData.confirmPassword}
              onChange={handleChange}
            />
          </div>
        </div>

        {/* Đồng ý chính sách bảo mật */}
        <div className="flex items-center">
          <input type="checkbox" style={{ height: "20px", width: "20px" }} />
          <label htmlFor="" className="ps-2">
            Tôi đã đọc và đồng ý với
            <Link to="/" className="text-primary">
              {" "}
              Điều khoản dịch vụ{" "}
            </Link>
            và
            <Link to="/" className="text-primary">
              {" "}
              Chính sách bảo mật{" "}
            </Link>
            của FindJob
          </label>
        </div>

        {/* Button Submit */}
        <div className="w-full rounded-lg bg-primary mt-3 py-3 hover:opacity-80">
          <button
            type="submit"
            className="btn btn-primary w-full text-white text-xl"
          >
            Đăng ký
          </button>
        </div>
      </form>

      {/* Phương thức khác */}
      <div className="">
        <p className="py-6 text-center text-slate-400">Hoặc đăng nhập bằng</p>
        <div className="flex justify-between items-center">
          <Link
            className="w-1/2 me-2 py-2 rounded-lg bg-primary text-lg text-white flex items-center justify-center hover:opacity-80"
            style={{ backgroundColor: "#e73b2f" }}
          >
            <FontAwesomeIcon
              icon={faGoogle}
              className="text-white text-lg pe-6"
            />
            Google
          </Link>
          <Link
            className="w-1/2 ms-2 py-2 rounded-lg bg-primary text-lg text-white flex items-center justify-center hover:opacity-80"
            style={{ backgroundColor: "#1877f2" }}
          >
            <FontAwesomeIcon
              icon={faSquareFacebook}
              className="text-white pe-6 text-2xl"
            />
            Facebook
          </Link>
        </div>

        {/* Đồng ý chính sách bảo mật mạng xã hội */}
        <div className="pt-4 flex items-center">
          <input type="checkbox" style={{ height: "20px", width: "20px" }} />
          <label htmlFor="" className="ps-2">
            Tôi đã đọc và đồng ý với
            <Link to="/" className="text-primary">
              {" "}
              Điều khoản dịch vụ{" "}
            </Link>
            và
            <Link to="/" className="text-primary">
              {" "}
              Chính sách bảo mật{" "}
            </Link>
            của FindJob
          </label>
        </div>
        {/* end: Đồng ý chính sách bảo mật mạng xã hội */}
      </div>

      {/* Đã có tài khoản */}
      <p className="pt-4 text-center">
        Bạn đã có tài khoản?{" "}
        <Link to="/login" className="text-primary">
          Đăng nhập
        </Link>
      </p>
    </div>
  );
};

export default SignUpForm;
