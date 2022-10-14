import { setupWorker } from 'msw'
import { courseCardHandler } from './handlers/courseCardHandler'

// This configures a Service Worker with the given request handlers.
export const worker = setupWorker(...courseCardHandler)