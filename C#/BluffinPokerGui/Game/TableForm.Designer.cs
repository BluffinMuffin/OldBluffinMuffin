namespace BluffinPokerGui.Game
{
    partial class TableForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.btnFold = new System.Windows.Forms.Button();
            this.btnCall = new System.Windows.Forms.Button();
            this.btnRaise = new System.Windows.Forms.Button();
            this.nudRaise = new System.Windows.Forms.NumericUpDown();
            this.flowLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudRaise)).BeginInit();
            this.SuspendLayout();
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.Controls.Add(this.btnFold);
            this.flowLayoutPanel1.Controls.Add(this.btnCall);
            this.flowLayoutPanel1.Controls.Add(this.btnRaise);
            this.flowLayoutPanel1.Controls.Add(this.nudRaise);
            this.flowLayoutPanel1.FlowDirection = System.Windows.Forms.FlowDirection.TopDown;
            this.flowLayoutPanel1.Location = new System.Drawing.Point(874, 52);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(146, 501);
            this.flowLayoutPanel1.TabIndex = 3;
            // 
            // btnFold
            // 
            this.btnFold.Enabled = false;
            this.btnFold.Location = new System.Drawing.Point(3, 3);
            this.btnFold.Name = "btnFold";
            this.btnFold.Size = new System.Drawing.Size(135, 23);
            this.btnFold.TabIndex = 0;
            this.btnFold.Text = "FOLD";
            this.btnFold.UseVisualStyleBackColor = true;
            this.btnFold.Click += new System.EventHandler(this.btnFold_Click);
            // 
            // btnCall
            // 
            this.btnCall.Enabled = false;
            this.btnCall.Location = new System.Drawing.Point(3, 32);
            this.btnCall.Name = "btnCall";
            this.btnCall.Size = new System.Drawing.Size(135, 23);
            this.btnCall.TabIndex = 1;
            this.btnCall.Text = "CALL";
            this.btnCall.UseVisualStyleBackColor = true;
            this.btnCall.Click += new System.EventHandler(this.btnCall_Click);
            // 
            // btnRaise
            // 
            this.btnRaise.Enabled = false;
            this.btnRaise.Location = new System.Drawing.Point(3, 61);
            this.btnRaise.Name = "btnRaise";
            this.btnRaise.Size = new System.Drawing.Size(135, 23);
            this.btnRaise.TabIndex = 2;
            this.btnRaise.Text = "RAISE";
            this.btnRaise.UseVisualStyleBackColor = true;
            this.btnRaise.Click += new System.EventHandler(this.btnRaise_Click);
            // 
            // nudRaise
            // 
            this.nudRaise.Enabled = false;
            this.nudRaise.Location = new System.Drawing.Point(3, 90);
            this.nudRaise.Name = "nudRaise";
            this.nudRaise.Size = new System.Drawing.Size(132, 20);
            this.nudRaise.TabIndex = 3;
            // 
            // TableForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1018, 687);
            this.Controls.Add(this.flowLayoutPanel1);
            this.Name = "TableForm";
            this.Text = "C# Poker Table";
            this.Controls.SetChildIndex(this.flowLayoutPanel1, 0);
            this.flowLayoutPanel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.nudRaise)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private System.Windows.Forms.Button btnFold;
        private System.Windows.Forms.Button btnCall;
        private System.Windows.Forms.Button btnRaise;
        private System.Windows.Forms.NumericUpDown nudRaise;


    }
}