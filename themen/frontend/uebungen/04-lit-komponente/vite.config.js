import { defineConfig } from 'vite';

export default defineConfig({
  test: {
    // Tests brauchen ein DOM – jsdom stellt document, Elemente und Events ohne Browser bereit.
    environment: 'jsdom',
  },
});
