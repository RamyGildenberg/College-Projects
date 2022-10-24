import { useCallback, useState } from "react";

const useHttp = (requestFunction) => {
  const [httpState, setHttpState] = useState({
    status: null,
    data: null,
    error: null,
  });

  const sendRequest = useCallback(
    async (requestData) => {
      setHttpState({ status: "PENDING", data: null, error: null });
      try {
        const responseData = await requestFunction(requestData);
        setHttpState({ status: "COMPLETED", data: responseData, error: null });
      } catch (error) {
        setHttpState({
          status: "COMPLETED",
          data: null,
          error: error.message || "Something went wrong!",
        });
      }
    },
    [requestFunction]
  );

  return {
    ...httpState,
    sendRequest,
  };
};

export default useHttp;
