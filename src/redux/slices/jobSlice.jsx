import { createSlice } from "@reduxjs/toolkit";
import minhvietgroup from "../../assets/images/image_products/minh_viet_group.webp";
import vina68 from "../../assets/images/image_products/vina68.webp";

const initState = {
  jobs: [
    {
      id: "1",
      image: vina68,
      title: "Software Engineer",
      company: "Tập đoàn FPT",
      location: "Hồ Chí Minh",
      salary: "20tr - 30tr",
      description: "Work with a team of engineers to build the next big thing",
      date: "2021-01-01",
      status: "Applied",
    },
    {
      id: "2",
      image: minhvietgroup,
      title: "Product Manager",
      company: "Facebook",
      location: "Menlo Park, CA",
      salary: "12tr - 25tr",
      description: "Work with a team of engineers to build the next big thing",
      date: "2021-01-01",
      status: "Interview",
    },
    {
      id: "3",
      image: minhvietgroup,
      title: "Kế toán tổng hợp",
      company: "Công ty cổ phần tập đoàn điện khí",
      location: "Hà Nội",
      salary: "14tr - 16tr",
      description: "Làm việc tại Hà Nội, có kinh nghiệm 2 năm",
      date: "2021-01-01",
      status: "Interview",
    },
    {
      id: "4",
      image: minhvietgroup,
      title: "Content Creator Tiktok",
      company: "Công ty trách nhiệm hữu hạn thương mại dịch vụ SALTECH",
      location: "Hà Nội",
      salary: "12tr - 25tr",
      description: "Work with a team of engineers to build the next big thing",
      date: "2021-01-01",
      status: "Interview",
    },
  ],
};

const jobSlice = createSlice({
  name: "jobs",
  initialState: initState,
  reducers: {},
});

export default jobSlice.reducer;
