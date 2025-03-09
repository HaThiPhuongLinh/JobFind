import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  cvList: [],
  status: "idle",
  error: null,
  // test
  isSubmit: false,
};

const cvSlice = createSlice({
  name: "cv",
  initialState,
  reducers: {
    addCV: (state, action) => {
      state.cvList.push(action.payload);
    },
    // test
    submitCV: (state) => {
      state.isSubmit = true;
    },
  },
});

export const { addCV, submitCV } = cvSlice.actions;
export default cvSlice.reducer;
