import { useQuery, UseQueryOptions } from 'react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { courseKeys } from '../queryKeys';
import fetcher from '../../../api/fetcher';

const useCourses = ({
  filters,
  options,
}: {
  filters?: string;
  options?: UseQueryOptions<
    AxiosResponse<Course[]>,
    AxiosError,
    Course[],
    (readonly ['courses', 'list', { readonly filters: string | undefined }])[]
  >;
}) =>
  useQuery([courseKeys.list(filters)], () => fetcher.get(`/admin/courses`), {
    select: data => data.data,
    ...options,
  });

export default useCourses;
