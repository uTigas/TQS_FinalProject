
const APIWrapper = {
  backendURI: "/", // TODO: Check if this is necessary
  privateAPI: "private/api/v1/",
  adminAPI: "admin/api/v1/",

  fetchUserDetails: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'user', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching User details', error);
    }
  },

  fetchAdminDetails: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'admin', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Admin details', error);
    }
  },

  fetchStationList: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'stations', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Stations', error);
    }
  },

  addStation: async (stationName: string, stationLines: number) => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'stations', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: stationName, numberOfLines: stationLines })
      });
    } catch (error) {
      console.error('Error adding Station', error);
    }
  },

  editStation: async (oldStationName: string, newStationName: string, stationLines: number) => {
    try {
      return await fetch(`${APIWrapper.backendURI + APIWrapper.adminAPI}stations/${oldStationName}`, {
        method: 'PUT',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: newStationName, numberOfLines: stationLines })
      });
    } catch (error) {
      console.error('Error editing Station', error);
    }
  },

}
export default APIWrapper;