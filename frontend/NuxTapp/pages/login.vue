<template>
  <div class="login">
    <h2>Login</h2>
    <form @submit.prevent="submitLogin">
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit">Login</button>
      <p v-if="error">{{ error }}</p>
    </form>
  </div>
</template>

<script setup lang="ts">
const email = ref('')
const password = ref('')
const error = ref('')
const config = useRuntimeConfig()

async function submitLogin() {
  try {
    const res = await $fetch('/login', {
      baseURL: config.public.apiBase,
      method: 'POST',
      body: { email: email.value, password: password.value },
      credentials: 'include'
    })
    await navigateTo('/profile')
  } catch (e: any) {
    error.value = e?.data?.message || 'Login failed'
  }
}
</script>
