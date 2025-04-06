<script setup lang="ts">
import { onMounted, reactive, ref, watch } from 'vue'
import ViewToDoLists from './components/ViewToDoLists.vue'
import { ToDoListRequest } from './features/toDoList/ToDoListRequest'
import { useToDoListStore } from './features/toDoList/ToDoListStore'
import type { ToDoList } from './features/toDoList/ToDoList'

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const isLoading = ref(true)
const localToDoLists = reactive(new Array<ToDoList>())

onMounted(async () => {
  await store.getAllToDoLists(req) // Wait for the API call to complete
  localToDoLists.push(...store.allToDoListState) // Update the local state with the fetched data
  isLoading.value = false // Set loading to false after data is fetched
})

// Watch for changes in the prop and update the local copy
watch(
  () => store.allToDoListState,
  (newToDoLists) => {
    console.log('New ToDoLists:', newToDoLists)
    localToDoLists.splice(0, localToDoLists.length, ...newToDoLists) // Replace the local array contents
  },
  { deep: true },
)
</script>

<template>
  <ViewToDoLists :to-do-lists="localToDoLists" :is-loading="isLoading" />
</template>

<style scoped></style>
