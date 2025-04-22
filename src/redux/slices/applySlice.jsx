// src/redux/slices/applicationSlice.js
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import applicationApi from "../../api/applicationApi";

// Async thunk: lấy danh sách application theo jobId
export const fetchAllApplications = createAsyncThunk(
  "application/fetchByJob",
  async (jobId, { rejectWithValue }) => {
    try {
      const response = await applicationApi.getApplicationHistory(jobId);
      return response.data; // hoặc response nếu bạn đã xử lý response.data trong axiosClient
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message);
    }
  }
);

const applicationSlice = createSlice({
  name: "application",
  initialState: {
    list: [],
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchAllApplications.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAllApplications.fulfilled, (state, action) => {
        state.loading = false;
        state.list = action.payload;
      })
      .addCase(fetchAllApplications.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload || "Lỗi không xác định.";
      });
  },
});

export default applicationSlice.reducer;
