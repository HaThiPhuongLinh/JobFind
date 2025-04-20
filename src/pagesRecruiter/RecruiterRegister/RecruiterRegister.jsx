import { useState, useEffect } from "react";
<<<<<<< HEAD

import { useNavigate } from "react-router-dom";
import authApi from "./../../api/authApi";
import industryApi from "./../../api/industryApi";
=======
import { Link, useNavigate } from "react-router-dom";
>>>>>>> eebf06313744317fd7198f098be6ca0b826d195b
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import authApi from "../../api/authApi";
import industryApi from "../../api/industryApi";
import logo from "../../assets/logo.png";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowRight } from "@fortawesome/free-solid-svg-icons";

const RecruiterRegister = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    phone: "",
    password: "",
    companyName: "",
    industry: "",
    logoPath: "",
    website: "",
    description: "",
  });

  const [businessFields, setBusinessFields] = useState([]);
  const [selectedIndustries, setSelectedIndustries] = useState(null);

  useEffect(() => {
    const fetchIndustries = async () => {
      try {
        const response = await industryApi.getAll();
        setBusinessFields(response);
      } catch (error) {
        console.error("Lỗi khi lấy dữ liệu ngành nghề", error);
      }
    };

    fetchIndustries();
  }, []);

  const handleChangeFormData = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleFileChange = (e) => {
    setFormData({ ...formData, logoPath: e.target.files[0] });
  };

  const handleIndustryChange = (e) => {
    const selectedId = e.target.value;
    const selected = businessFields.find(
      (option) => option.industryId.toString() === selectedId
    );
    setSelectedIndustries(selected || null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const form = new FormData();
    form.append("email", formData.email);
    form.append("phone", formData.phone);
    form.append("password", formData.password);
    form.append("companyName", formData.companyName);
    form.append("website", formData.website);
    form.append("industryIds", selectedIndustries?.industryId || "");
    form.append("description", formData.description);
    form.append("role", "COMPANY");

    if (formData.logoPath) {
      form.append("logoPath", formData.logoPath);
    }

    try {
      await authApi.register(form);
      toast.success("Đăng ký thành công!", { autoClose: 3000 });
      setTimeout(() => navigate("/recruiter/home"), 3000);
    } catch (error) {
      toast.error("Đăng ký thất bại. Vui lòng thử lại!", { autoClose: 3000 });
    }
  };

  return (
<<<<<<< HEAD
    <div className="min-h-screen flex">
      {/* ========== Thẻ div chứa form thông tin ============== */}
      <div className="w-1/2 bg-white p-12 overflow-y-auto">
        {/* form thông tin */}
        <form
          onSubmit={handleSubmit}
          className="mx-auto bg-white p-8 rounded-md w-fit"
        >
          {/* ========== Thông tin tài khoản ============== */}
          <p className="text-xl font-semibold pb-4">Thông tin tài khoản</p>
          {formFields
            .filter((f) => f.name === "email")
            .map((field) => (
              <div className="form-group mb-3" key={field.name}>
                <label htmlFor={field.name} className="font-semibold pb-2">
                  {field.label}{" "}
                  {field.required && <span className="text-red-600">*</span>}
                </label>
                <input
                  type={field.type}
                  name={field.name}
                  value={formData[field.name]}
                  className="form-control w-full p-2 border border-gray-300 rounded-md outline-none"
                  placeholder={field.placeholder}
                  onChange={handleChangeFormData}
                  required={field.required}
                  onInvalid={(e) =>
                    e.target.setCustomValidity(field.errorMsg || "")
                  }
                  onInput={(e) => e.target.setCustomValidity("")}
                />
              </div>
            ))}

          <div className="flex gap-4">
            {formFields
              .filter((f) => f.name === "phone" || f.name === "password")
              .map((field) => (
                <div className="form-group mb-3 w-1/2" key={field.name}>
                  <label htmlFor={field.name} className="font-semibold pb-2">
                    {field.label}{" "}
                    {field.required && <span className="text-red-600">*</span>}
                  </label>

                  <input
                    type={field.type}
                    name={field.name}
                    value={formData[field.name]}
                    className="form-control w-full p-2 border border-gray-300 rounded-md outline-none"
                    placeholder={field.placeholder}
                    onChange={handleChangeFormData}
                    required={field.required}
                    onInvalid={(e) =>
                      e.target.setCustomValidity(field.errorMsg || "")
                    }
                    onInput={(e) => e.target.setCustomValidity("")}
                  />
                </div>
              ))}
          </div>
          {formFields
            .filter(
              (f) =>
                !["phone", "password", "email", "logoPath"].includes(f.name)
            )
            .map((field) => (
              <div className="form-group mb-3" key={field.name}>
                <label htmlFor={field.name} className="font-semibold pb-2">
                  {field.label}{" "}
                  {field.required && <span className="text-red-600">*</span>}
                </label>
                {field.type === "textarea" ? (
                  <textarea
                    name={field.name}
                    placeholder={field.placeholder}
                    required={field.required}
                    className="form-control w-full p-2 border border-gray-300 rounded-md outline-none"
                    value={formData[field.name]}
                    onChange={handleChangeFormData}
                  />
                ) : field.type === "select" ? (
                  <select
                    name="industry"
                    value={
                      selectedIndustries ? selectedIndustries.industryId : ""
                    }
                    onChange={handleIndustryChange}
                    className="form-control w-full p-2 border border-gray-300 rounded-md outline-none"
                  >
                    <option value="">Chọn ngành nghề</option>
                    {businessFields.map((option) => (
                      <option key={option.industryId} value={option.industryId}>
                        {option.name}
                      </option>
                    ))}
                  </select>
                ) : (
                  <input
                    type={field.type}
                    name={field.name}
                    value={formData[field.name]}
                    className="form-control w-full p-2 border border-gray-300 rounded-md outline-none"
                    placeholder={field.placeholder}
                    onChange={handleChangeFormData}
                    required={field.required}
                    onInvalid={(e) =>
                      e.target.setCustomValidity(field.errorMsg || "")
                    }
                    onInput={(e) => e.target.setCustomValidity("")}
                  />
                )}

                {field.name === "companyName" && (
                  <div className="form-group mt-3">
                    <label htmlFor="logoPath" className="font-semibold pb-2">
                      Logo công ty <span className="text-red-600">*</span>
                    </label>
                    <input
                      type="file"
                      name="logoPath"
                      accept="image/*"
                      onChange={handleFileChange}
                      className="form-control w-full p-2 border border-gray-300 rounded-md outline-none"
                      required
                    />
                  </div>
                )}
              </div>
            ))}
          <button
            type="submit"
            className="!rounded-button whitespace-nowrap w-full bg-gradient-to-r from-blue-500 to-blue-400 hover:from-blue-600 hover:to-blue-500 text-white py-3 px-4 rounded-lg transition duration-300 flex items-center justify-center cursor-pointer shadow-sm hover:shadow-md"
          >
            Hoàn thành đăng ký
          </button>
          {/* Login Link */}
          <div className="text-center mt-4">
            <p className="text-sm text-gray-600">
              Bạn đã có tài khoản?
              <span className="ml-1 text-blue-500 hover:text-blue-600 font-medium transition-colors cursor-pointer">
                Đăng nhập
              </span>
            </p>
          </div>
        </form>
        <ToastContainer />
        {/* end: form thông tin */}
=======
    <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-br from-green-100 via-white to-blue-100 px-4">
      <div>
        <img src={logo} alt="Logo" className="w-44 mx-auto object-contain" />
>>>>>>> eebf06313744317fd7198f098be6ca0b826d195b
      </div>
      <form
        onSubmit={handleSubmit}
        className="bg-white shadow-md rounded-xl p-8 w-full max-w-4xl"
      >
        <h2 className="text-2xl font-bold text-center mb-6">
          Đăng ký nhà tuyển dụng
        </h2>

<<<<<<< HEAD
      {/* =================== div chứa background ============= */}
      <div className="w-1/2 relative">
        {/* Background */}
        <div
          className="absolute inset-0 bg-cover bg-center"
          style={{
            backgroundImage: `url('/visual_company_register.jpg')`,
          }}
        >
          <div className="absolute inset-0 bg-blue-500/10 backdrop-blur-[2px]"></div>
        </div>
        {/* Overlay Content */}
        <div className="relative h-full flex flex-col items-center justify-center text-white p-12 text-center">
          <h2 className="text-4xl font-bold mb-6">Start Hiring Today!</h2>
          <p className="text-lg mb-8 max-w-md">
            Join thousands of companies who trust us to help them find the
            perfect candidates for their teams.
          </p>
          <div className="grid grid-cols-2 gap-8 w-full max-w-lg">
            <div className="bg-white/10 backdrop-blur-sm rounded-xl p-6">
              <div className="text-3xl font-bold mb-2">1M+</div>
              <div className="text-sm">Active Job Seekers</div>
            </div>
            <div className="bg-white/10 backdrop-blur-sm rounded-xl p-6">
              <div className="text-3xl font-bold mb-2">48h</div>
              <div className="text-sm">Average Hiring Time</div>
            </div>
            <div className="bg-white/10 backdrop-blur-sm rounded-xl p-6">
              <div className="text-3xl font-bold mb-2">92%</div>
              <div className="text-sm">Employer Satisfaction</div>
            </div>
            <div className="bg-white/10 backdrop-blur-sm rounded-xl p-6">
              <div className="text-3xl font-bold mb-2">24/7</div>
              <div className="text-sm">Dedicated Support</div>
            </div>
          </div>
        </div>
        {/* Button go to home */}
        <div className="absolute bottom-24 left-1/2 transform -translate-x-1/2 text-center w-1/2">
          <a
            href="/"
            className="block bg-white text-blue-600 font-semibold px-5 py-3 rounded-full shadow-md hover:bg-blue-100 transition-all duration-200 text-center"
          >
            Go to Home
          </a>
        </div>
=======
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block mb-1 font-medium text-sm">
              Email <span className="text-red-600">*</span>
            </label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChangeFormData}
              required
              className="input"
            />
          </div>

          <div>
            <label className="block mb-1 font-medium text-sm">
              Số điện thoại <span className="text-red-600">*</span>
            </label>
            <input
              type="number"
              name="phone"
              value={formData.phone}
              onChange={handleChangeFormData}
              required
              className="input"
            />
          </div>

          <div>
            <label className="block mb-1 font-medium text-sm">
              Mật khẩu <span className="text-red-600">*</span>
            </label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChangeFormData}
              required
              className="input"
            />
          </div>

          <div>
            <label className="block mb-1 font-medium text-sm">
              Tên công ty <span className="text-red-600">*</span>
            </label>
            <input
              type="text"
              name="companyName"
              value={formData.companyName}
              onChange={handleChangeFormData}
              required
              className="input"
            />
          </div>

          <div>
            <label className="block mb-1 font-medium text-sm">
              Ngành nghề <span className="text-red-600">*</span>
            </label>
            <select
              name="industry"
              value={selectedIndustries?.industryId || ""}
              onChange={handleIndustryChange}
              required
              className="input"
            >
              <option value="">Chọn ngành nghề *</option>
              {businessFields.map((field) => (
                <option key={field.industryId} value={field.industryId}>
                  {field.name}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block mb-1 font-medium text-sm">
              Logo công ty <span className="text-red-600">*</span>
            </label>
            <input
              type="file"
              name="logoPath"
              accept="image/*"
              onChange={handleFileChange}
              className="input"
              required
            />
          </div>

          <div>
            <label className="block mb-1 font-medium text-sm">
              Website công ty
            </label>
            <input
              type="text"
              name="website"
              value={formData.website}
              onChange={handleChangeFormData}
              className="input"
            />
          </div>
        </div>

        <div className="mt-4">
          <label className="block mb-1 font-medium text-sm">
            Mô tả công ty
          </label>
          <textarea
            name="description"
            value={formData.description}
            onChange={handleChangeFormData}
            className="input"
          />
        </div>

        <div className="mt-6 flex justify-between items-center">
          <p>
            Đã có tài khoản?{" "}
            <Link
              to="/recruiter/login"
              className="text-blue-600 font-medium hover:underline"
            >
              Đăng nhập
            </Link>
          </p>
          <button
            type="submit"
            className="bg-green-600 text-white px-6 py-2 rounded-md font-semibold hover:bg-green-700"
          >
            Đăng ký
          </button>
        </div>
        <div className="flex justify-center mt-6">
        <Link
          to="/home"
          className="flex items-center gap-2 text-sm font-medium text-gray-700 hover:text-black transition"
        >
          Tới trang tìm việc
          <FontAwesomeIcon icon={faArrowRight} />
        </Link>
>>>>>>> eebf06313744317fd7198f098be6ca0b826d195b
      </div>
        <ToastContainer />
      </form>
      
      <style>{`
        .input {
          padding: 0.5rem;
          border: 1px solid #d1d5db;
          border-radius: 0.5rem;
          width: 100%;
          outline: none;
        }
        .input:focus {
          border-color: #3b82f6;
        }
      `}</style>
    </div>
  );
}
export default RecruiterRegister;
