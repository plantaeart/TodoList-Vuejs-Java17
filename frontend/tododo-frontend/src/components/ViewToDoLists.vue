<script setup lang="ts">
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ref, watch, computed } from 'vue' // Added computed
import DisplayToDoListItem from './toDoList/DisplayToDoListItem.vue'
import AddToDoList from './toDoList/addToDoList/AddToDoList.vue'
import DisplayTaskItem from './task/DisplayTaskItem.vue'
import DisplaySubTaskItem from './subTask/DisplaySubTaskItem.vue'
import type { ToDoList } from '@/features/toDoList/ToDoList'
import appEnv from 'app-env'

const store = useToDoListStore()
const localToDoLists = ref<ToDoList[]>([]) // Local state to hold fetched data
const debug = ref(appEnv.VITE_DEBUG)
const isTesting = appEnv.VITE_IS_TEST

const props = defineProps({
  toDoLists: {
    type: Object as () => Array<ToDoList>, // Specify the type of the prop
    required: true, // Make it required
  },
  isLoading: {
    type: Boolean, // Specify the type of the prop
    required: true, // Make it required
  },
})

localToDoLists.value = props.toDoLists // Initialize local state with the prop value

// Watch for changes in the prop and update the local copy
watch(
  () => props.toDoLists,
  (newToDoLists) => {
    localToDoLists.value = newToDoLists // Update the local copy when the prop changes
  },
  { deep: true },
)

// Calculate columns for the masonry layout - Pinterest style
const columnCount = 3 // Number of columns for medium screens and above
const masonryColumns = computed(() => {
  // Create an array of 'columnCount' empty arrays
  const columns = Array.from({ length: columnCount }, () => [] as ToDoList[])

  // Distribute items across columns optimally
  localToDoLists.value.forEach((toDoList, index) => {
    // Add each todo list to the appropriate column
    columns[index % columnCount].push(toDoList)
  })

  return columns
})
</script>

<template>
  <div class="m-4">
    <div class="flex flex-col items-center mb-1">
      <h1
        class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-gray-900 md:text-5xl lg:text-6xl dark:text-grey"
      >
        Tododo
      </h1>
      <p v-if="debug">{{ 'Is testing : ' + isTesting }}</p>
      <h2 class="text-l underline">To do and do !</h2>
      <div v-if="debug">
        <p v-if="isLoading">Loading...</p>
        <p v-else>
          {{ 'Count the number of lists : ' + store.sizeToDoList }}
        </p>
      </div>
    </div>
    <div v-if="!isLoading">
      <AddToDoList />
    </div>
    <div v-else class="flex flex-row justify-center"><p class="text-5xl">Loading...</p></div>

    <!-- Pinterest-style masonry layout -->
    <div class="flex flex-col md:flex-row w-full gap-4">
      <!-- Create a column for each array in masonryColumns -->
      <div
        v-for="(column, columnIndex) in masonryColumns"
        :key="columnIndex"
        class="w-full md:w-1/3 flex flex-col gap-4"
      >
        <!-- Render each todo list in this column -->
        <div
          v-for="toDoList in column"
          :key="toDoList.id"
          :class="[!toDoList.isCompleted ? toDoList.color?.color : 'bg-gray-200']"
          class="rounded-lg w-full shadow-md p-4"
          :data-cy="`toDoList-${toDoList.id}`"
        >
          <p v-if="debug">Todo list name: {{ toDoList.name }} and id: {{ toDoList.id }}</p>
          <DisplayToDoListItem :to-do-list="toDoList" />
          <div class="flex flex-col ml-6" v-for="task in toDoList.tasks" :key="task.id">
            <DisplayTaskItem :task="task" :id-list="toDoList.id as number" />
            <div
              class="flex flex-row items-center ml-10"
              v-for="subTask in task.subTasks"
              :key="subTask.id"
            >
              <DisplaySubTaskItem
                :sub-task="subTask"
                :id-list="toDoList.id as number"
                :id-task="task.id as number"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
