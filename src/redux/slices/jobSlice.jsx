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
      experience: 4,
      category: "Marketing",
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
      experience: 0,
      category: "IT",
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
      experience: 2,
      salary: "14tr - 16tr",
      category: "Dev Python",
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
      experience: 3,
      category: "Dev Java",
      description: "Work with a team of engineers to build the next big thing",
      date: "2021-01-01",
      status: "Interview",
    },
  ],
  filterJobs: [],
};

const jobSlice = createSlice({
  name: "jobs",
  initialState: { ...initState, filterJobs: initState.jobs },
  reducers: {
    filterJob: (state, action) => {
      const { key, value } = action.payload;
      state.filterJobs = state.jobs.filter((job) => {
        if (key === "Địa điểm") {
          return value === "Tất cả" ? true : job.location === value;
        }
        if (key === "Mức lương") {
          return value === "Tất cả"
            ? true
            : value === "Dưới 5 triệu"
            ? job.salary.includes("tr") && parseInt(job.salary) < 5
            : value === "5 - 10 triệu"
            ? job.salary.includes("tr") &&
              parseInt(job.salary) >= 5 &&
              parseInt(job.salary) <= 10
            : value === "10 - 20 triệu"
            ? job.salary.includes("tr") &&
              parseInt(job.salary) >= 10 &&
              parseInt(job.salary) <= 20
            : job.salary.includes("tr") && parseInt(job.salary) > 20;
        }
        if (key === "Kinh nghiệm") {
          return value === "Tất cả"
            ? true
            : value === "Chưa có kinh nghiệm"
            ? job.experience === 0
            : value === "Dưới 1 năm"
            ? job.experience > 0 && job.experience < 1
            : value === "1 - 2 năm"
            ? job.experience >= 1 && job.experience <= 2
            : job.experience > 2;
        }
        if (key === "Ngành nghề") {
          return value === "Tất cả" ? true : job.category === value;
        }
        return true;
      });
    },
  },
});

export const { filterJob } = jobSlice.actions;
export default jobSlice.reducer;
