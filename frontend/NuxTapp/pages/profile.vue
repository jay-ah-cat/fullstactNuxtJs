<template>
  <div>
    <h2 v-if="user">User Profile</h2>
    <p v-if="user">Logged in as: {{ user }}</p>
    <p v-else>Unauthorized</p>
    <button v-if="user"  @click="logout">Logout</button>
  </div>
</template>

<script setup lang="ts">
const user = ref<string | null>(null)
const config = useRuntimeConfig()

onMounted(async () => {
  try {
    const res = await $fetch<{ user: string }>(`/me`, {
      baseURL: config.public.apiBase,
      credentials: 'include'
    })
    user.value = res.user
  } catch {
    user.value = null
  }
})

async function logout() {
  try {
    await $fetch('/logout', {
      baseURL: config.public.apiBase,
      method: 'POST',
      credentials: 'include'
    })
    
    // 🔁 Redirect to login page
    const router = useRouter()
    router.push('/login')
  } catch (error) {
    console.error('Logout failed:', error)
  }
}
</script>
