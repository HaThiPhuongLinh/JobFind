import { createSlice } from "@reduxjs/toolkit";

import jobs from "../../data/jobs";

const JOBS_PER_PAGE = 6;

const initState = {
  jobs: jobs,
  filterJobs: [],
  selectedJob: null,
  relatedJobs: [],
  jobsSaved: [],
};

const jobSlice = createSlice({
  name: "jobs",
  initialState: { ...initState, filterJobs: initState.jobs },
  reducers: {
    setSelectedJob: (state, action) => {
      // lưu job được chọn
      state.selectedJob = action.payload;

      // lọc ra các job liên quan
      state.relatedJobs = state.jobs.filter(
        (job) =>
          job.category === action.payload.category &&
          job.id !== action.payload.id
      );
    },
    setFilterJob: (state, action) => {
      // lưu job được chọn
      state.selectedJob = action.payload;

      state.filterJobs = state.selectedJob;
    },
    likeJob: (state, action) => {
      const job = action.payload;
      // console.log(job); // in ra dc
      if (state.jobsSaved.some((j) => j.id === job.id)) {
        state.jobsSaved = state.jobsSaved.filter((j) => j.id !== job.id);
      } else {
        state.jobsSaved = [...state.jobsSaved, job];
      }
    },
    unSaveJob: (state, action) => {
      const job_id = action.payload;
      state.jobsSaved = state.jobsSaved.filter((j) => j.id !== job_id);
    },
    filterJobByCategory: (state, action) => {
      const categories = action.payload; // danh sách subcategories đã chọn
      // console.log(categories);
      state.filterJobs = state.jobs.filter((job) =>
        categories.includes(job.category)
      );
      // console.log(state.filterJobs);
    },
  },
});

// Lấy số lượng job
export const countJob = (state) => state.jobs.jobs.length;
export const maxPage = (state) =>
  Math.ceil(state.jobs.filterJobs.length / JOBS_PER_PAGE);

export const {
  filterJob,
  relatedJob,
  paginateJobs,
  setSelectedJob,
  likeJob,
  unSaveJob,
  filterJobByCategory,
  setFilterJob,
} = jobSlice.actions;
export default jobSlice.reducer;
