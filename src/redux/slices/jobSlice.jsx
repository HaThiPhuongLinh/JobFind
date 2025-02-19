import { createSlice } from "@reduxjs/toolkit";

import jobs from "../../data/jobs";

const JOBS_PER_PAGE = 6;

const initState = {
  jobs: jobs,
  currentPage: 1,
  filterJobs: [],
  paginationJobs: jobs.slice(0, JOBS_PER_PAGE),
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
      // Reset lại trang khi filter
      state.currentPage = 1;
      state.paginationJobs = state.filterJobs.slice(0, JOBS_PER_PAGE);
    },
    relatedJob: (state, action) => {
      const { category } = action.payload;
      state.filterJobs = state.jobs.filter((job) => job.category === category);
    },
    paginateJobs: (state, action) => {
      state.currentPage = action.payload;
      const start = (state.currentPage - 1) * JOBS_PER_PAGE;
      state.paginationJobs = state.filterJobs.slice(
        start,
        start + JOBS_PER_PAGE
      );
    },
  },
});

// Lấy số lượng job
export const countJob = (state) => state.jobs.jobs.length;
export const maxPage = (state) =>
  Math.ceil(state.jobs.filterJobs.length / JOBS_PER_PAGE);

export const { filterJob, relatedJob, paginateJobs } = jobSlice.actions;
export default jobSlice.reducer;
