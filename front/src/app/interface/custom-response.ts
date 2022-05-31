import {Server} from "./server";

export interface CustomResponse {
  timestamp: Date;
  statusCode: number;
  status: string;
  errorMessage: string;
  userMessage: string;
  devMessage: string;
  data: {servers?: Server[], server?:Server};
}
