import request from '@/utils/request'

export function getMovieList(params) {
    return request({
        url: '/movie/page',
        method: 'get',
        params
    })
}

export function addMovie(data) {
    return request({
        url: '/movie',
        method: 'post',
        data
    })
}

export function updateMovie(id, data) {
    return request({
        url: `/movie/${id}`,
        method: 'put',
        data
    })
}

export function deleteMovie(id) {
    return request({
        url: `/movie/${id}`,
        method: 'delete'
    })
} 