import { configureStore } from "@reduxjs/toolkit";
import jobReucer from "./slices/jobSlice";
import locationReducer from "./slices/locationsSlice";

const store = configureStore({
  reducer: {
    jobs: jobReucer,
    locations: locationReducer,
  },
});

export default store;
