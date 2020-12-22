import axios from 'axios';
export const getBaseUrl =() => {
    let prto = window.location.protocol;
    let host = window.location.hostname;
    // let port = window.location.host.indexOf('localhost:3000') > -1 ? 3001 : 3001;
    let port = window.location.host.indexOf('localhost:3000') > -1 ? 3001 : window.location.port;
    return `${prto}//${host}:${port}/`;
}

const Axios = axios.create({
    baseURL: getBaseUrl(),
    timeout: 30 * 1000
})

export default Axios;

export const formatDate = (myDate) => {
    const now = new Date(myDate);
    const month = (now.getMonth() + 1).toString().padStart(2, '0');
    const day = now.getDate().toString().padStart(2,'0');
    return `${now.getFullYear()}-${month}-${day}`;
}

export const getInputChange = (eventData) => {
    const target = eventData.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;
    return ({[name]: value});
}

// export const ApiEndpoints= {
//     person: '/person',
//     person2: '/person2'
// }