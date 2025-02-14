import { configureStore } from "@reduxjs/toolkit";
import jobReucer from "./slices/jobSlice";

const store = configureStore({
  reducer: {
    jobs: jobReucer,
  },
});

export default store;
