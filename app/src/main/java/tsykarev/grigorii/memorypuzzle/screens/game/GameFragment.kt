package tsykarev.grigorii.memorypuzzle.screens.game

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tsykarev.grigorii.memorypuzzle.R
import tsykarev.grigorii.memorypuzzle.database.MemoryPuzzleDatabase
import tsykarev.grigorii.memorypuzzle.databinding.GameFragmentBinding
import tsykarev.grigorii.memorypuzzle.models.BoardSize
import tsykarev.grigorii.memorypuzzle.models.MemoryCard
import tsykarev.grigorii.memorypuzzle.models.MemoryGame
import tsykarev.grigorii.memorypuzzle.utils.DEFAULT_ICONS


class GameFragment : Fragment() {

    companion object {
        private const val TAG = "GameFragment" 
    }

    private lateinit var clRoot: ConstraintLayout
    private lateinit var adapter: MemoryBoardAdapter
    private lateinit var memoryGame: MemoryGame
    private lateinit var viewModel: GameViewModel
    private lateinit var rvBoard: RecyclerView
    private lateinit var tvNumMoves: TextView
    private lateinit var tvNumPairs: TextView
    private lateinit var view: View
    private lateinit var binding: GameFragmentBinding
    private lateinit var toolbar: Toolbar

    private var boardSize: BoardSize = BoardSize.EASY

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.i("GameFragment", "Called ViewModelProvider.get")

        binding = DataBindingUtil.inflate(
            inflater, R.layout.game_fragment, container, false)
//        val application = requireNotNull(this.activity).application
//        val dao = MemoryPuzzleDatabase.getInstance(application).getMemoryPuzzleDao()
//        val viewModelFactory = GameViewModelFactory(dao, application)
//        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)

        rvBoard = binding.rvBoard
        view = binding.root

        binding.diff.setOnClickListener {
            setNewDifficulty()
        }

        setupBoard()

        return binding.root
    }

    private fun showAlertDialog(title: String, view: View?, positiveClickListener: View.OnClickListener) {
        AlertDialog.Builder(this.context)
            .setTitle(title)
            .setView(view)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                positiveClickListener.onClick(null)
            }.show()
    }

    private fun setNewDifficulty(){
        val boardSizeView = LayoutInflater.from(this.context).inflate(R.layout.dialog_board_size, null)
        val radioGroupSize = boardSizeView.findViewById<RadioGroup>(R.id.radio_groupe)

        when(boardSize){
            BoardSize.EASY -> radioGroupSize.check(R.id.rbEasy)
            BoardSize.MEDIUM -> radioGroupSize.check(R.id.rbMedium)
            BoardSize.HARD -> radioGroupSize.check(R.id.rbHard)
        }

        showAlertDialog("Choose new size", boardSizeView, View.OnClickListener {
            boardSize = when(radioGroupSize.checkedRadioButtonId){
                R.id.rbEasy -> BoardSize.EASY
                R.id.rbMedium -> BoardSize.MEDIUM
                else -> BoardSize.HARD
            }
            setupBoard()
        })
    }

    private fun setupBoard(){
        tvNumMoves = binding.tvNumMoves
        tvNumPairs = binding.tvNumPairs

        memoryGame = MemoryGame(boardSize)

        adapter = MemoryBoardAdapter(this.context, boardSize, memoryGame.cards, object: MemoryBoardAdapter.CardClickListener {
            override fun onCardClicked(position: Int) {
                UpdateGameWithFlip(position)
            }
        })



        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this.context, boardSize.getWidth())

    }

    private fun UpdateGameWithFlip(position: Int) {
        if(memoryGame.haveWonGame()){
            Snackbar.make(clRoot, "You already won!", Snackbar.LENGTH_SHORT).show()
            return
        }
        if(memoryGame.isCardFaceUp(position)){
            return
        }
        if(memoryGame.flipCard(position)){
            tvNumPairs.text = "Pairs: ${memoryGame.numPairsFound} / ${boardSize.getNumPairs()}"
            if (memoryGame.haveWonGame()){
                Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_scoreFragment)
            }
        }

        tvNumMoves.text = "Moves: ${memoryGame.getNumMoves()}"
        adapter.notifyDataSetChanged()
    }
}