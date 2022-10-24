export const BE_URL = process.env.REACT_APP_BE_URL || "/api/";

const serialize = (obj) => {
  if (!obj) return "";
  const str = [];
  for (const p in obj) {
    if (obj.hasOwnProperty(p)) {
      if (typeof obj[p] == typeof []) {
        str.push(
          encodeURIComponent(p) +
            "[]=" +
            obj[p].join("&" + encodeURIComponent(p) + "[]=")
        );
      } else {
        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
      }
    }
  }
  return "?" + str.join("&");
};

const sendRequest = async (sendType, url, dataToSend) => {
  const response = await fetch(url, {
    method: sendType,
    body: JSON.stringify(dataToSend),
    headers: {
      "Content-Type": "application/json",
      Accept: "*/*",
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "POST, GET, DELETE, PUT",
    },
  });
  if (!response.ok) {
    const err = await response.json();
    console.log("err => ", err);
    throw new Error(
      `Error #${err.status || 500}: ${err.message || "someting went wrong :("}`
    );
  }
  if (response.headers.get("content-type")?.includes("application/json"))
    return await response.json();
  return {};
};

export const GetData = async (dataName, queryData) => {
  const queryString = serialize(queryData);
  const URL = BE_URL + dataName + queryString;
  return await sendRequest("GET", URL, undefined);
};

export const AddData = async (dataToAdd, dataName) => {
  const URL = BE_URL + dataName;
  return await sendRequest("POST", URL, dataToAdd);
};

export const UpdateData = async (dataName, dataToUpdate) => {
  const URL = BE_URL + dataName;
  return await sendRequest("PUT", URL, dataToUpdate);
};

export const DeleteData = async (dataName, dataToUpdate) => {
  const URL = BE_URL + dataName;
  return await sendRequest("DELETE", URL, dataToUpdate);
};
