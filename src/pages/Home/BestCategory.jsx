import batDongSan from "../../assets/images/image_category/bat-dong-san.webp";
import CongNgheThongTin from "../../assets/images/image_category/cong-nghe-thong-tin.webp";
import DichVuKhachHang from "../../assets/images/image_category/dich-vu-khach-hang.webp";
import HanhChinhVanPhong from "../../assets/images/image_category/hanh-chinh-van-phong.webp";
import KeToan from "../../assets/images/image_category/ke-toan-kiem-toan.webp";
import kinhDoanhBanHang from "../../assets/images/image_category/kinh-doanh-ban-hang.webp";
import marketing from "../../assets/images/image_category/marketing-truyen-thong.webp";
import nganHangTaiChinh from "../../assets/images/image_category/ngan-hang-tai-chinh.webp";

const category = [
  {
    name: "Bất động sản",
    count: "9.768",
    image: batDongSan,
  },
  {
    name: "Công nghệ thông tin",
    count: "11.253",
    image: CongNgheThongTin,
  },
  {
    name: "Dịch vụ khách hàng",
    count: "9.768",
    image: DichVuKhachHang,
  },
  {
    name: "Hành chính văn phòng",
    count: "9.768",
    image: HanhChinhVanPhong,
  },
  {
    name: "Kế toán - Kiểm toán",
    count: "9.768",
    image: KeToan,
  },
  {
    name: "Kinh doanh - Bán hàng",
    count: "9.768",
    image: kinhDoanhBanHang,
  },
  {
    name: "Marketing truyền thông",
    count: "9.768",
    image: marketing,
  },
  {
    name: "Ngân hàng - Tài chính",
    count: "9.768",
    image: nganHangTaiChinh,
  },
];

const BestCategory = () => {
  return (
    <div className="container mx-auto py-8">
      <p className="text-2xl text-primary font-bold">Top ngành nghề nổi bật</p>
      <div className="grid grid-cols-4 gap-6 pt-4">
        {category.map((item) => (
          <div
            key={item.name}
            className="flex flex-col justify-center items-center bg-slate-200 rounded-lg cursor-pointer hover:bg-white
            hover:shadow-lg transition duration-300 ease-in-out border hover:border-green-400"
            style={{ height: "200px" }}
          >
            <img
              src={item.image}
              alt="icon"
              style={{ width: "100px", height: "100px" }}
              className=""
            />
            <p className="pt-4 font-bold">{item.name}</p>
            <p className="text-primary text-sm pt-2">{item.count} việc làm</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default BestCategory;
