export const courseKeys = {
  all: ['courses'] as const,
  one: (id: number) => [...courseKeys.all, { id }] as const,
  lists: () => [...courseKeys.all, 'list'] as const,
  list: (filters?: string) => [...courseKeys.lists(), { filters }] as const,
};
