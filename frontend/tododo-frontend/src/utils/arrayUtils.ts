/**
 * Rearranges the IDs of objects in an array to be sequential (1 to N) and sorts the array by the `id` property.
 * @param array - The array of objects to rearrange and sort.
 * @returns The modified array with sequential IDs and sorted by `id`.
 */
export function rearrangeArrayIds<T extends { id?: number }>(array: T[]): T[] {
  if (array.length > 0) {
    // Assign sequential IDs (1 to N)
    array.forEach((item, index) => {
      item.id = index + 1
    })
  }

  // Sort the array by the `id` property
  array.sort((a, b) => a.id! - b.id!)

  return array
}
