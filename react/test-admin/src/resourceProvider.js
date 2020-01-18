import { fetchUtils } from 'react-admin';
import authProvider from "./authProvider";

const apiUrl = 'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1';
const httpClient = async (url, options = {}) => {
    options.headers.set('Authorization', await authProvider.getHeader());
    return fetchUtils.fetchJson(url, options);
}
export default {
    getList: async (resource, params) => {
        if(resource == "tests"){
            const auth = await authProvider.getUserMail();
            const email = await auth.attributes.email
            resource = resource + "/recruiter/" + email ;
        }
        const url = `${apiUrl}/${resource}`;
        const  result = httpClient(url, {headers: new Map()}).then(({ headers, json }) => (
            {
            data: json,
            total: parseInt(json.length, 10),

        }));
        return result;
    },

    getOne: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`, {headers: new Map()}).then(({ json }) => ({
            data: json,
        })),

    // getMany: (resource, params) => {
    //     const query = {
    //         filter: JSON.stringify({ id: params.ids }),
    //     };
    //     const url = `${apiUrl}/${resource}?${stringify(query)}`;
    //     return httpClient(url).then(({ json }) => ({ data: json }));
    // },
    //
    // getManyReference: (resource, params) => {
    //     const { page, perPage } = params.pagination;
    //     const { field, order } = params.sort;
    //     const query = {
    //         sort: JSON.stringify([field, order]),
    //         range: JSON.stringify([(page - 1) * perPage, page * perPage - 1]),
    //         filter: JSON.stringify({
    //             ...params.filter,
    //             [params.target]: params.id,
    //         }),
    //     };
    //     const url = `${apiUrl}/${resource}?${stringify(query)}`;
    //
    //     return httpClient(url).then(({ headers, json }) => ({
    //         data: json,
    //         total: parseInt(headers.get('content-range').split('/').pop(), 10),
    //     }));
    // },

    update: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`, {
            method: 'PUT',
            body: JSON.stringify(params.data),
            headers: new Map()
        }).then(({ json }) => {
            if(json.errorMessage){
                alert(json.errorMessage)
                return ;
            }
            return { data: json}
        }),

    // updateMany: (resource, params) => {
    //     const query = {
    //         filter: JSON.stringify({ id: params.ids}),
    //     };
    //     return httpClient(`${apiUrl}/${resource}?${stringify(query)}`, {
    //         method: 'PUT',
    //         body: JSON.stringify(params.data),
    //     }).then(({ json }) => ({ data: json }));
    // },

    create: (resource, params) => {
        console.log(JSON.stringify(params.data))
        httpClient(`${apiUrl}/${resource}`, {
            method: 'POST',
            body: JSON.stringify(params.data),
            headers: new Map()
        }).then(({ json }) => {
            if(resource === "tests"){
                document.location.href="/#/tests";
            }else{
                document.location.href="/";
            }
        })},

    delete: (resource, params) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'DELETE',
            body: JSON.stringify(params.previousData),
            headers: new Map()
        }).then(({ json }) => ({ data: json })),

    // deleteMany: (resource, params) => {
    //     const query = {
    //         filter: JSON.stringify({ id: params.ids}),
    //     };
    //     return httpClient(`${apiUrl}/${resource}?${stringify(query)}`, {
    //         method: 'DELETE',
    //         body: JSON.stringify(params.data),
    //     }).then(({ json }) => ({ data: json }));
    // }
};
